package az.abb.product.mcp;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductListRequest;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.service.ProductService;
import io.temporal.failure.ApplicationFailure;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerProductsToolImpl implements CustomerProductsTool {

    private final ProductService productService;

    @Override
    @Tool(description = "Get all customer products (cards and accounts)")
    public Flux<ProductDto> getCustomerProducts(
            @ToolParam(description = "PIN for authentication (optional)") String pin,
            @ToolParam(description = "Customer CIF (optional)") String cif,
            @ToolParam(description = "User ID (optional)") String userId,
            @ToolParam(description = "Product types to include - comma separated (CARD, ACCOUNT, LOAN, "
                    + "DEPOSIT, STORED_CARD)") String types,
            @ToolParam(description = "Include hidden products (default: false)") Boolean includeHidden) {
        log.info("Starting customer products processing for userId: {}, cif: {}", userId, cif);

        try {
            // Parse types string to Set<ProductType>
            Set<ProductType> productTypes = parseProductTypes(types);
            
            // Parse userId to UUID
            UUID parsedUserId = userId != null ? UUID.fromString(userId) : null;
            
            // Parse CIF to Set<String>
            Set<String> cifs = cif != null ? Set.of(cif) : null;

            ProductListRequest request = ProductListRequest.builder()
                        .identifierOptions(az.abb.product.dto.offlineproduct.IdentifierOptions.builder()
                                .pin(pin)
                                .cifs(cifs)
                                .userId(parsedUserId)
                                .build())
                        .types(productTypes)
                        .hidden(includeHidden != null ? includeHidden : false)
                        .build();

            return productService.getCustomerProducts(request)
                    .doOnError(error -> {
                        log.error("Error retrieving customer products", error);
                        if (error.getMessage() != null && error.getMessage().contains("not found")) {
                            throw ApplicationFailure.newNonRetryableFailure(
                                    "Customer products not found for user: " + userId,
                                    "NotFoundError");
                        }
                    })
                    .doOnComplete(() -> log.info("Successfully retrieved customer products for user: {}", userId))
                    .doOnNext(result -> log.debug("Customer product: {}", result.getId()));

        } catch (Exception e) {
            log.error("Error processing customer products request", e);
            return Flux.error(e);
        }
    }

    private Set<ProductType> parseProductTypes(String types) {
        if (types == null || types.trim().isEmpty()) {
            return Set.of(ProductType.CARD, ProductType.ACCOUNT);
        }
        
        return Arrays.stream(types.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(ProductType::valueOf)
                .collect(Collectors.toSet());
    }
}
