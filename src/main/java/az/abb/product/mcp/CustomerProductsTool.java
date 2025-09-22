package az.abb.product.mcp;

import az.abb.product.dto.offlineproduct.ProductDto;
import reactor.core.publisher.Flux;

public interface CustomerProductsTool {

    Flux<ProductDto> getCustomerProducts(
            String pin,
            String cif,
            String userId,
            String types,
            Boolean includeHidden
    );
}
