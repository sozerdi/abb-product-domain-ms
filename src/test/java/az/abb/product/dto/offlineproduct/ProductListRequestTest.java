package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProductListRequestTest {

    @Test
    void productListRequest_ShouldCreateWithBuilder() {
        // Given
        IdentifierOptions identifierOptions = IdentifierOptions.builder()
                .pin("1234")
                .cifs(Set.of("CIF123"))
                .userId(UUID.randomUUID())
                .build();
        Boolean hidden = false;
        Boolean primary = true;
        Boolean expired = false;
        Set<ProductType> types = Set.of(ProductType.CARD, ProductType.ACCOUNT);
        String productNumber = "1234567890123456";
        String productId = "test-id";

        // When
        ProductListRequest request = ProductListRequest.builder()
                .identifierOptions(identifierOptions)
                .hidden(hidden)
                .primary(primary)
                .expired(expired)
                .types(types)
                .productNumber(productNumber)
                .productId(productId)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(identifierOptions, request.identifierOptions());
        assertEquals(hidden, request.hidden());
        assertEquals(primary, request.primary());
        assertEquals(expired, request.expired());
        assertEquals(types, request.types());
        assertEquals(productNumber, request.productNumber());
        assertEquals(productId, request.productId());
    }

    @Test
    void productListRequest_ShouldCreateWithMinimalData() {
        // Given
        Set<ProductType> types = Set.of(ProductType.CARD);

        // When
        ProductListRequest request = ProductListRequest.builder()
                .types(types)
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.identifierOptions());
        assertNull(request.hidden());
        assertNull(request.primary());
        assertNull(request.expired());
        assertEquals(types, request.types());
        assertNull(request.productNumber());
        assertNull(request.productId());
    }

    @Test
    void productListRequest_ShouldCreateWithNullValues() {
        // When
        ProductListRequest request = ProductListRequest.builder()
                .identifierOptions(null)
                .hidden(null)
                .primary(null)
                .expired(null)
                .types(null)
                .productNumber(null)
                .productId(null)
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.identifierOptions());
        assertNull(request.hidden());
        assertNull(request.primary());
        assertNull(request.expired());
        assertNull(request.types());
        assertNull(request.productNumber());
        assertNull(request.productId());
    }
}
