package az.abb.product.activity.impl;

import az.abb.product.activity.ProductInfoActivity;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.service.ProductService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(workers = "product-info-worker")
@RequiredArgsConstructor
public class ProductInfoActivityImpl implements ProductInfoActivity {
    private final ProductService productInfoService;

    @Override
    public ProductDto getProductInfo(ProductRequest productRequest) {
        ActivityExecutionContext ctx = Activity.getExecutionContext();
        ActivityCompletionClient completionClient = ctx.getWorkflowClient().newActivityCompletionClient();
        byte[] taskToken = ctx.getTaskToken();
        
        log.debug("Activity: Starting getProductInfo for request: {}", productRequest);
        
        productInfoService.getProductInfo(productRequest)
                .subscribe(
                    result -> {
                        log.debug("Activity: Successfully completed getProductInfo for product: {}", 
                                result != null ? result.getId() : "null");
                        completionClient.complete(taskToken, result);
                    },
                    error -> {
                        log.error("Activity: Error getting product info", error);
                        completionClient.completeExceptionally(taskToken, (Exception) error);
                    }
                );
        
        ctx.doNotCompleteOnReturn();
        
        // It will be returned async
        return null;
    }
}
