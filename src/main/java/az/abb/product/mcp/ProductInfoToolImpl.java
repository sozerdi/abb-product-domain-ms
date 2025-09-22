package az.abb.product.mcp;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
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
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductInfoToolImpl implements ProductInfoTool {

    private final ProductService productService;

    @Override
    @Tool(description = "Get product information by product ID or product number")
    public Mono<ProductDto> getProductInfo(
            @ToolParam(description = "Product ID (optional)") String productId,
            @ToolParam(description = "Product number (optional)") String productNumber,
            @ToolParam(description = "PIN for authentication (optional)") String pin,
            @ToolParam(description = "Customer CIF (optional)") String cif,
            @ToolParam(description = "User ID (optional)") String userId,
            @ToolParam(description = "Product types to include - comma separated (CARD, ACCOUNT, LOAN, DEPOSIT, "
                    + "STORED_CARD)") String types,
            @ToolParam(description = "Include hidden products (default: false)") Boolean includeHidden) {
        log.info("Starting product info processing for productId: {}, productNumber: {}", productId, productNumber);

        try {
            // Parse types string to Set<ProductType>
            Set<ProductType> productTypes = parseProductTypes(types);
            
            // Parse userId to UUID
            UUID parsedUserId = userId != null ? UUID.fromString(userId) : null;
            
            // Parse CIF to Set<String>
            Set<String> cifs = cif != null ? Set.of(cif) : null;

            ProductRequest request = ProductRequest.builder()
                    .productId(productId)
                    .productNumber(productNumber)
                    .type(productTypes != null && !productTypes.isEmpty() ? productTypes.iterator().next() : null)
                    .build();

            return productService.getProductInfo(request)
                    .doOnError(error -> {
                        log.error("Error retrieving product info", error);
                        if (error.getMessage() != null && error.getMessage().contains("not found")) {
                            throw ApplicationFailure.newNonRetryableFailure(
                                    "Product not found: " + (productId != null ? productId : productNumber),
                                    "NotFoundError");
                        }
                    })
                    .doOnSuccess(result -> log.info("Successfully retrieved product info for: {}", 
                            productId != null ? productId : productNumber))
                    .doOnNext(result -> log.debug("Product info result: {}", result));

        } catch (Exception e) {
            log.error("Error processing product info request", e);
            return Mono.error(e);
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
