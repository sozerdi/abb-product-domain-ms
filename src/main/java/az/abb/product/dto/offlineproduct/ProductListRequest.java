package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductListRequest(IdentifierOptions identifierOptions,
                                 Boolean hidden,
                                 Boolean primary,
                                 Boolean expired,
                                 Set<ProductType> types,
                                 String productNumber,
                                 String productId) {
}
