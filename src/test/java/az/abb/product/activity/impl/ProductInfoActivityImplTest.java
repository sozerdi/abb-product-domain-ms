package az.abb.product.activity.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.service.ProductService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ProductInfoActivityImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ActivityExecutionContext activityExecutionContext;

    @Mock
    private ActivityCompletionClient activityCompletionClient;

    @Mock
    private io.temporal.client.WorkflowClient workflowClient;

    @InjectMocks
    private ProductInfoActivityImpl productInfoActivity;

    private ProductRequest productRequest;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productRequest = ProductRequest.builder()
                .productId("test-id")
                .type(ProductType.CARD)
                .build();

        productDto = new ProductDto();
        productDto.setId("test-id");
        productDto.setType(ProductType.CARD);
    }

    @Test
    void getProductInfo_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getProductInfo(any(ProductRequest.class)))
                .thenReturn(Mono.just(productDto));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            ProductDto result = productInfoActivity.getProductInfo(productRequest);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void getProductInfo_WithServiceError_ShouldCompleteExceptionally() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getProductInfo(any(ProductRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Service Error")));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            ProductDto result = productInfoActivity.getProductInfo(productRequest);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void getProductInfo_WithNullRequest_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getProductInfo(null))
                .thenReturn(Mono.empty());

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            ProductDto result = productInfoActivity.getProductInfo(null);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }
}