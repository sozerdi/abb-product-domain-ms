package az.abb.product.mcp;

import az.abb.product.dto.offlineproduct.ProductDto;
import reactor.core.publisher.Mono;

public interface ProductInfoTool {

    Mono<ProductDto> getProductInfo(
            String productId,
            String productNumber,
            String pin,
            String cif,
            String userId,
            String types,
            Boolean includeHidden
    );
}
