package az.abb.product.mcp;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.enums.PartyType;
import az.abb.product.service.ProductRestrictionService;
import io.temporal.failure.ApplicationFailure;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRestrictionToolImpl implements ProductRestrictionTool {

    private final ProductRestrictionService productRestrictionService;

    @Override
    @Tool(description = "Check if a product is restricted for a party type")
    public Mono<Boolean> checkProductRestriction(
            @ToolParam(description = "Product code to check") String productCode,
            @ToolParam(description = "Party type (SENDER, RECEIVER, etc.)") String partyType) {
        log.info("Starting product restriction check for productCode: {}, partyType: {}", productCode, partyType);

        try {
            // Parse partyType string to PartyType enum
            PartyType parsedPartyType = PartyType.valueOf(partyType.toUpperCase());

            ProductRestrictionRequest request = ProductRestrictionRequest.builder()
                    .productCode(productCode)
                    .partyType(parsedPartyType)
                    .build();

            return productRestrictionService.isProductRestricted(request)
                    .doOnError(error -> {
                        log.error("Error checking product restriction", error);
                        if (error.getMessage() != null && error.getMessage().contains("not found")) {
                            throw ApplicationFailure.newNonRetryableFailure(
                                    "Product restriction check failed for: " + productCode,
                                    "NotFoundError");
                        }
                    })
                    .doOnSuccess(result -> log.info("Successfully checked product restriction for productCode: {}, "
                                    + "result: {}",
                            productCode, result))
                    .doOnNext(result -> log.debug("Product restriction result: {}", result));

        } catch (IllegalArgumentException e) {
            log.error("Invalid party type: {}", partyType, e);
            return Mono.error(ApplicationFailure.newNonRetryableFailure(
                    "Invalid party type: " + partyType + ". Valid values: "
                            + Arrays.toString(PartyType.values()),
                    "ValidationError"));
        } catch (Exception e) {
            log.error("Error processing product restriction request", e);
            return Mono.error(e);
        }
    }
}
