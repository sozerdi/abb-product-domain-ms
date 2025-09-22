package az.abb.product.activity.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductListRequest;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.service.ProductService;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class CustomerProductsActivityImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ActivityExecutionContext activityExecutionContext;

    @Mock
    private ActivityCompletionClient activityCompletionClient;

    @Mock
    private io.temporal.client.WorkflowClient workflowClient;

    @InjectMocks
    private CustomerProductsActivityImpl customerProductsActivity;

    private ProductListRequest productListRequest;
    private List<ProductDto> productDtos;

    @BeforeEach
    void setUp() {
        productListRequest = ProductListRequest.builder()
                .types(java.util.Set.of(ProductType.CARD, ProductType.ACCOUNT))
                .hidden(false)
                .primary(true)
                .expired(false)
                .build();

        ProductDto productDto1 = new ProductDto();
        productDto1.setId("card-id");
        productDto1.setType(ProductType.CARD);

        ProductDto productDto2 = new ProductDto();
        productDto2.setId("account-id");
        productDto2.setType(ProductType.ACCOUNT);

        productDtos = List.of(productDto1, productDto2);
    }

    @Test
    void getCustomerProducts_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getCustomerProducts(any(ProductListRequest.class)))
                .thenReturn(Flux.fromIterable(productDtos));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            List<ProductDto> result = customerProductsActivity.getCustomerProducts(productListRequest);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void getCustomerProducts_WithServiceError_ShouldCompleteExceptionally() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getCustomerProducts(any(ProductListRequest.class)))
                .thenReturn(Flux.error(new RuntimeException("Service Error")));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            List<ProductDto> result = customerProductsActivity.getCustomerProducts(productListRequest);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void getCustomerProducts_WithEmptyResult_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getCustomerProducts(any(ProductListRequest.class)))
                .thenReturn(Flux.empty());

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            List<ProductDto> result = customerProductsActivity.getCustomerProducts(productListRequest);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void getCustomerProducts_WithNullRequest_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productService.getCustomerProducts(null))
                .thenReturn(Flux.empty());

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            List<ProductDto> result = customerProductsActivity.getCustomerProducts(null);

            // Then
            assertNull(result); // Should return null for async completion
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }
}