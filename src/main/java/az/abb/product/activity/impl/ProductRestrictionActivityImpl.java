package az.abb.product.activity.impl;

import az.abb.product.activity.ProductRestrictionActivity;
import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.service.ProductRestrictionService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(workers = "product-restriction-worker")
@RequiredArgsConstructor
public class ProductRestrictionActivityImpl implements ProductRestrictionActivity {
    private final ProductRestrictionService productRestrictionService;

    @Override
    public void checkProductRestriction(ProductRestrictionRequest productRestrictionRequest) {
        ActivityExecutionContext ctx = Activity.getExecutionContext();
        ActivityCompletionClient completionClient = ctx.getWorkflowClient().newActivityCompletionClient();
        byte[] taskToken = ctx.getTaskToken();
        
        log.debug("Activity: Starting checkProductRestriction for request: {}", productRestrictionRequest);
        
        productRestrictionService.isProductRestricted(productRestrictionRequest)
                .subscribe(
                    isRestricted -> {
                        if (isRestricted) {
                            log.warn("Activity: Product is restricted for party type: {}", 
                                    productRestrictionRequest.partyType().name());
                            completionClient.completeExceptionally(taskToken, 
                                    ApplicationFailure.newNonRetryableFailure(
                                            productRestrictionRequest.partyType().name()
                                                    + " product is restricted",
                                            "ValidationError"
                                    ));
                        } else {
                            log.debug("Activity: Product is not restricted for party type: {}", 
                                    productRestrictionRequest.partyType().name());
                            completionClient.complete(taskToken, null);
                        }
                    },
                    error -> {
                        log.error("Activity: Error checking product restriction", error);
                        completionClient.completeExceptionally(taskToken, (Exception) error);
                    }
                );
        
        ctx.doNotCompleteOnReturn();
    }
}
