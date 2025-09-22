package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductRequestTest {

    @Test
    void productRequest_ShouldCreateWithBuilder() {
        // Given
        String productId = "test-id";
        String productNumber = "1234567890123456";
        ProductType type = ProductType.CARD;

        // When
        ProductRequest request = ProductRequest.builder()
                .productId(productId)
                .productNumber(productNumber)
                .type(type)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(productId, request.productId());
        assertEquals(productNumber, request.productNumber());
        assertEquals(type, request.type());
    }

    @Test
    void productRequest_ShouldCreateWithPartialData() {
        // Given
        String productId = "test-id";
        ProductType type = ProductType.ACCOUNT;

        // When
        ProductRequest request = ProductRequest.builder()
                .productId(productId)
                .type(type)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(productId, request.productId());
        assertNull(request.productNumber());
        assertEquals(type, request.type());
    }

    @Test
    void productRequest_ShouldCreateWithOnlyType() {
        // Given
        ProductType type = ProductType.STORED_CARD;

        // When
        ProductRequest request = ProductRequest.builder()
                .type(type)
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.productId());
        assertNull(request.productNumber());
        assertEquals(type, request.type());
    }
}
