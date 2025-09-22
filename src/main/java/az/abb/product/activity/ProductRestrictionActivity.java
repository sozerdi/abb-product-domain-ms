package az.abb.product.activity;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ProductRestrictionActivity {

    void checkProductRestriction(ProductRestrictionRequest productRestrictionRequest);
}