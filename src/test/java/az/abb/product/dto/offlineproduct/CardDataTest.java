package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CardDataTest {

    @Test
    void cardData_ShouldCreateWithDefaultConstructor() {
        // When
        CardData cardData = new CardData();

        // Then
        assertNotNull(cardData);
        assertNull(cardData.getNumber());
        assertNull(cardData.getExpiryDate());
        assertNull(cardData.getExpired());
        assertNull(cardData.getNetwork());
    }

    @Test
    void cardData_ShouldSetAndGetProperties() {
        // Given
        CardData cardData = new CardData();
        String number = "1234567890123456";
        String expiryDate = "12/25";
        Boolean expired = false;
        String network = "VISA";

        // When
        cardData.setNumber(number);
        cardData.setExpiryDate(expiryDate);
        cardData.setExpired(expired);
        cardData.setNetwork(network);

        // Then
        assertEquals(number, cardData.getNumber());
        assertEquals(expiryDate, cardData.getExpiryDate());
        assertEquals(expired, cardData.getExpired());
        assertEquals(network, cardData.getNetwork());
    }

    @Test
    void cardData_WithConstructor_ShouldCreateCorrectly() {
        // Given
        String number = "1234567890123456";
        String expiryDate = "12/25";

        // When
        CardData cardData = new CardData();
        cardData.setNumber(number);
        cardData.setExpiryDate(expiryDate);

        // Then
        assertNotNull(cardData);
        assertEquals(number, cardData.getNumber());
        assertEquals(expiryDate, cardData.getExpiryDate());
    }
}
