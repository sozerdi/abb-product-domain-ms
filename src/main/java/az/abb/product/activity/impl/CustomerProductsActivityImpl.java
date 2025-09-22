package az.abb.product.activity.impl;

import az.abb.product.activity.CustomerProductsActivity;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductListRequest;
import az.abb.product.service.ProductService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.spring.boot.ActivityImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(workers = "customer-products-worker")
@RequiredArgsConstructor
public class CustomerProductsActivityImpl implements CustomerProductsActivity {
    private final ProductService productInfoService;

    @Override
    public List<ProductDto> getCustomerProducts(ProductListRequest productRequest) {
        ActivityExecutionContext ctx = Activity.getExecutionContext();
        ActivityCompletionClient completionClient = ctx.getWorkflowClient().newActivityCompletionClient();
        byte[] taskToken = ctx.getTaskToken();
        
        log.debug("Activity: Starting getCustomerProducts for request: {}", productRequest);
        
        productInfoService.getCustomerProducts(productRequest)
                .collectList()
                .subscribe(
                    result -> {
                        log.debug("Activity: Successfully completed getCustomerProducts with {} results",
                                result.size());
                        completionClient.complete(taskToken, result);
                    },
                    error -> {
                        log.error("Activity: Error getting customer products", error);
                        completionClient.completeExceptionally(taskToken, (Exception) error);
                    }
                );
        
        ctx.doNotCompleteOnReturn();
        
        // It will be returned async
        return null;
    }
}
