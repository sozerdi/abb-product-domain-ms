package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductRequest(ProductType type,
                             String productNumber,
                             String productId) {
}
