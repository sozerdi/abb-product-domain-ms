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
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class CustomerProductsToolImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CustomerProductsToolImpl customerProductsTool;

    @Test
    void getCustomerProducts_WithValidParameters_ShouldCallService() {
        // Given
        when(productService.getCustomerProducts(any()))
                .thenReturn(Flux.empty());

        // When
        ProductDto result = customerProductsTool.getCustomerProducts("1234", "CIF123", 
                "550e8400-e29b-41d4-a716-446655440000", "CARD,ACCOUNT", true).blockFirst();

        // Then
        assertNull(result);
    }

    @Test
    void getCustomerProducts_WithNullParameters_ShouldCallService() {
        // Given
        when(productService.getCustomerProducts(any()))
                .thenReturn(Flux.empty());

        // When
        ProductDto result = customerProductsTool.getCustomerProducts(null, null, null, null, null).blockFirst();

        // Then
        assertNull(result);
    }
}