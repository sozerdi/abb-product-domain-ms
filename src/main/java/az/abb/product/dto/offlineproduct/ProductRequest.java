package az.abb.product.dto.offlineproduct;

import java.util.Set;
import lombok.Builder;

@Builder
public record ProductRequest(IdentifierOptions identifierOptions,
                             Boolean includeHidden,
                             Set<ProductType> types,
                             String productNumber,
                             String productId) {
}
