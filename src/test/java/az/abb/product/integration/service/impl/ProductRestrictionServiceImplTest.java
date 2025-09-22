package az.abb.product.integration.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import az.abb.product.integration.client.TransferProductRestrictionClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductRestrictionServiceImplTest {

    @Mock
    private TransferProductRestrictionClient restrictionClient;

    @InjectMocks
    private ProductRestrictionServiceImpl productRestrictionService;

    @Test
    void isProductRestricted_WithNullRequest_ShouldReturnFalse() {
        // When
        Boolean result = productRestrictionService.isProductRestricted(null).block();

        // Then
        assertFalse(result);
    }
}