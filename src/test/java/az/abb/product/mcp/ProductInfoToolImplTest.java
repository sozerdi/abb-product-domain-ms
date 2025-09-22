package az.abb.product.mcp;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ProductInfoToolImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductInfoToolImpl productInfoTool;

    @Test
    void getProductInfo_WithValidParameters_ShouldCallService() {
        // Given
        when(productService.getProductInfo(any()))
                .thenReturn(Mono.empty());

        // When
        ProductDto result = productInfoTool.getProductInfo("test-id", "1234567890123456", 
                "1234", "CIF123", "550e8400-e29b-41d4-a716-446655440000", "CARD", true).block();

        // Then
        assertNull(result);
    }

    @Test
    void getProductInfo_WithNullParameters_ShouldCallService() {
        // Given
        when(productService.getProductInfo(any()))
                .thenReturn(Mono.empty());

        // When
        ProductDto result = productInfoTool.getProductInfo(null, null, null, null, null, null, null).block();

        // Then
        assertNull(result);
    }
}