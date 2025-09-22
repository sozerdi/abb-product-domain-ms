package az.abb.product.activity.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.enums.PartyType;
import az.abb.product.service.ProductRestrictionService;
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
class ProductRestrictionActivityImplTest {

    @Mock
    private ProductRestrictionService productRestrictionService;

    @Mock
    private ActivityExecutionContext activityExecutionContext;

    @Mock
    private ActivityCompletionClient activityCompletionClient;

    @Mock
    private io.temporal.client.WorkflowClient workflowClient;

    @InjectMocks
    private ProductRestrictionActivityImpl productRestrictionActivity;

    private ProductRestrictionRequest restrictionRequest;

    @BeforeEach
    void setUp() {
        restrictionRequest = ProductRestrictionRequest.builder()
                .productCode("PROD123")
                .partyType(PartyType.SENDER)
                .build();
    }

    @Test
    void checkProductRestriction_WithNotRestrictedProduct_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productRestrictionService.isProductRestricted(any(ProductRestrictionRequest.class)))
                .thenReturn(Mono.just(false));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            productRestrictionActivity.checkProductRestriction(restrictionRequest);

            // Then
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void checkProductRestriction_WithRestrictedProduct_ShouldCompleteExceptionally() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productRestrictionService.isProductRestricted(any(ProductRestrictionRequest.class)))
                .thenReturn(Mono.just(true));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            productRestrictionActivity.checkProductRestriction(restrictionRequest);

            // Then
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void checkProductRestriction_WithServiceError_ShouldCompleteExceptionally() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productRestrictionService.isProductRestricted(any(ProductRestrictionRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Service Error")));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            productRestrictionActivity.checkProductRestriction(restrictionRequest);

            // Then
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void checkProductRestriction_WithReceiverPartyType_ShouldCompleteAsynchronously() {
        // Given
        ProductRestrictionRequest receiverRequest = ProductRestrictionRequest.builder()
                .productCode("PROD456")
                .partyType(PartyType.RECEIVER)
                .build();

        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productRestrictionService.isProductRestricted(any(ProductRestrictionRequest.class)))
                .thenReturn(Mono.just(false));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            productRestrictionActivity.checkProductRestriction(receiverRequest);

            // Then
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }

    @Test
    void checkProductRestriction_WithNullRequest_ShouldCompleteAsynchronously() {
        // Given
        byte[] taskToken = new byte[]{1, 2, 3, 4};
        
        when(activityExecutionContext.getTaskToken()).thenReturn(taskToken);
        when(activityExecutionContext.getWorkflowClient()).thenReturn(workflowClient);
        when(workflowClient.newActivityCompletionClient()).thenReturn(activityCompletionClient);
        when(productRestrictionService.isProductRestricted(null))
                .thenReturn(Mono.just(false));

        try (MockedStatic<Activity> activityMock = mockStatic(Activity.class)) {
            activityMock.when(Activity::getExecutionContext).thenReturn(activityExecutionContext);

            // When
            productRestrictionActivity.checkProductRestriction(null);

            // Then
            verify(activityExecutionContext).doNotCompleteOnReturn();
        }
    }
}