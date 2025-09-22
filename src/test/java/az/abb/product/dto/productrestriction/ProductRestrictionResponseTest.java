package az.abb.product.dto.productrestriction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductRestrictionResponseTest {

    @Test
    void productRestrictionResponse_ShouldCreateWithBuilder() {
        // Given
        Boolean restricted = false;

        // When
        ProductRestrictionResponse response = ProductRestrictionResponse.builder()
                .restricted(restricted)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(restricted, response.getRestricted());
    }

    @Test
    void productRestrictionResponse_ShouldCreateWithRestrictedTrue() {
        // Given
        Boolean restricted = true;

        // When
        ProductRestrictionResponse response = ProductRestrictionResponse.builder()
                .restricted(restricted)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(restricted, response.getRestricted());
    }

    @Test
    void productRestrictionResponse_ShouldCreateWithMinimalData() {
        // Given
        Boolean restricted = false;

        // When
        ProductRestrictionResponse response = ProductRestrictionResponse.builder()
                .restricted(restricted)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(restricted, response.getRestricted());
    }

    @Test
    void productRestrictionResponse_ShouldCreateWithNullValues() {
        // When
        ProductRestrictionResponse response = ProductRestrictionResponse.builder()
                .restricted(null)
                .build();

        // Then
        assertNotNull(response);
        assertNull(response.getRestricted());
    }
}
