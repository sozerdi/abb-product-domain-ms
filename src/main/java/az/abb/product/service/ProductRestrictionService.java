package az.abb.product.service;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;

public interface ProductRestrictionService {
    Boolean isProductRestricted(ProductRestrictionRequest request);
}
