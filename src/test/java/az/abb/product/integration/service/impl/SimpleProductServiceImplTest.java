package az.abb.product.integration.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.integration.client.OfflineProductClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SimpleProductServiceImplTest {

    @Mock
    private OfflineProductClient offlineProductClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductInfo_WithNullRequest_ShouldReturnEmpty() {
        // When
        Mono<ProductDto> result = productService.getProductInfo(null);

        // Then
        assertNull(result.block());
    }

    @Test
    void getProductInfo_WithEmptyRequest_ShouldReturnEmpty() {
        // Given
        ProductRequest emptyRequest = ProductRequest.builder()
                .type(ProductType.CARD)
                .build();

        // When
        Mono<ProductDto> result = productService.getProductInfo(emptyRequest);

        // Then
        assertNull(result.block());
    }
}

