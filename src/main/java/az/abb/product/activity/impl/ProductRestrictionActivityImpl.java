package az.abb.product.activity.impl;

import az.abb.product.activity.ProductRestrictionActivity;
import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.service.ProductRestrictionService;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "product-restriction-worker")
@RequiredArgsConstructor
public class ProductRestrictionActivityImpl implements ProductRestrictionActivity {
    private final ProductRestrictionService productRestrictionService;

    @Override
    public void checkProductRestriction(ProductRestrictionRequest productRestrictionRequest) {
        if (productRestrictionService.isProductRestricted(productRestrictionRequest)) {
            throw ApplicationFailure.newNonRetryableFailure(
                    productRestrictionRequest.partyType().name() + " product is restricted",
                    "ValidationError"
            );
        }
    }
}
