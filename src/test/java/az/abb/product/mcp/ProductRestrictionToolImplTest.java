package az.abb.product.mcp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import az.abb.product.service.ProductRestrictionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ProductRestrictionToolImplTest {

    @Mock
    private ProductRestrictionService productRestrictionService;

    @InjectMocks
    private ProductRestrictionToolImpl productRestrictionTool;

    @Test
    void checkProductRestriction_WithValidParameters_ShouldCallService() {
        // Given
        when(productRestrictionService.isProductRestricted(any()))
                .thenReturn(Mono.just(false));

        // When
        Boolean result = productRestrictionTool.checkProductRestriction("PROD123", "SENDER").block();

        // Then
        assertFalse(result);
    }
}