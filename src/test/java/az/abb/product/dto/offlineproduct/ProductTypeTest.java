package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    void productType_ShouldHaveCorrectValues() {
        // Then
        assertEquals("CARD", ProductType.CARD.name());
        assertEquals("ACCOUNT", ProductType.ACCOUNT.name());
        assertEquals("STORED_CARD", ProductType.STORED_CARD.name());
    }

    @Test
    void productType_ShouldBeAccessible() {
        // When
        ProductType card = ProductType.CARD;
        ProductType account = ProductType.ACCOUNT;
        ProductType storedCard = ProductType.STORED_CARD;

        // Then
        assertNotNull(card);
        assertNotNull(account);
        assertNotNull(storedCard);
    }

    @Test
    void productType_FromString_ShouldWork() {
        // When & Then
        assertEquals(ProductType.CARD, ProductType.fromString("CARD"));
        assertEquals(ProductType.ACCOUNT, ProductType.fromString("ACCOUNT"));
        assertEquals(ProductType.STORED_CARD, ProductType.fromString("STORED_CARD"));
    }

    @Test
    void productType_ToString_ShouldWork() {
        // When & Then
        assertEquals("CARD", ProductType.CARD.toString());
        assertEquals("ACCOUNT", ProductType.ACCOUNT.toString());
        assertEquals("STORED_CARD", ProductType.STORED_CARD.toString());
    }
}

