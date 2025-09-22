package az.abb.product.service;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import reactor.core.publisher.Mono;

public interface ProductRestrictionService {
    Mono<Boolean> isProductRestricted(ProductRestrictionRequest request);
}
