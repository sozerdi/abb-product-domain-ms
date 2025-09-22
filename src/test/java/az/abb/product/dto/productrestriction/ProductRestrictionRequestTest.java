package az.abb.product.dto.productrestriction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import az.abb.product.enums.PartyType;
import org.junit.jupiter.api.Test;

class ProductRestrictionRequestTest {

    @Test
    void productRestrictionRequest_ShouldCreateWithBuilder() {
        // Given
        String productCode = "PROD123";
        PartyType partyType = PartyType.SENDER;

        // When
        ProductRestrictionRequest request = ProductRestrictionRequest.builder()
                .productCode(productCode)
                .partyType(partyType)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(productCode, request.productCode());
        assertEquals(partyType, request.partyType());
    }

    @Test
    void productRestrictionRequest_ShouldCreateWithReceiverType() {
        // Given
        String productCode = "PROD456";
        PartyType partyType = PartyType.RECEIVER;

        // When
        ProductRestrictionRequest request = ProductRestrictionRequest.builder()
                .productCode(productCode)
                .partyType(partyType)
                .build();

        // Then
        assertNotNull(request);
        assertEquals(productCode, request.productCode());
        assertEquals(partyType, request.partyType());
    }

    @Test
    void productRestrictionRequest_ShouldCreateWithNullValues() {
        // When
        ProductRestrictionRequest request = ProductRestrictionRequest.builder()
                .productCode(null)
                .partyType(null)
                .build();

        // Then
        assertNotNull(request);
        assertNull(request.productCode());
        assertNull(request.partyType());
    }
}
