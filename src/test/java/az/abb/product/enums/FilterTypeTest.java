package az.abb.product.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class FilterTypeTest {

    @Test
    void filterType_ShouldHaveCorrectValues() {
        // Then
        assertEquals("CARD_NUMBER", FilterType.CARD_NUMBER.name());
        assertEquals("ACCOUNT_NUMBER", FilterType.ACCOUNT_NUMBER.name());
    }

    @Test
    void filterType_ShouldBeAccessible() {
        // When
        FilterType cardNumber = FilterType.CARD_NUMBER;
        FilterType accountNumber = FilterType.ACCOUNT_NUMBER;

        // Then
        assertNotNull(cardNumber);
        assertNotNull(accountNumber);
    }
}

