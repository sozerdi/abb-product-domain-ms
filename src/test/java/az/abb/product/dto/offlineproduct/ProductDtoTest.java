package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductDtoTest {

    @Test
    void productDto_ShouldCreateWithDefaultConstructor() {
        // When
        ProductDto productDto = new ProductDto();

        // Then
        assertNotNull(productDto);
        assertNull(productDto.getId());
        assertNull(productDto.getType());
        assertNull(productDto.getData());
    }

    @Test
    void productDto_ShouldSetAndGetProperties() {
        // Given
        ProductDto productDto = new ProductDto();
        String id = "test-id";
        ProductType type = ProductType.CARD;
        CardData cardData = new CardData();

        // When
        productDto.setId(id);
        productDto.setType(type);
        productDto.setData(cardData);

        // Then
        assertEquals(id, productDto.getId());
        assertEquals(type, productDto.getType());
        assertEquals(cardData, productDto.getData());
    }

    @Test
    void productDto_WithCardData_ShouldWorkCorrectly() {
        // Given
        ProductDto productDto = new ProductDto();
        CardData cardData = new CardData();
        cardData.setNumber("1234567890123456");
        cardData.setExpiryDate("12/25");

        // When
        productDto.setId("card-id");
        productDto.setType(ProductType.CARD);
        productDto.setData(cardData);

        // Then
        assertEquals("card-id", productDto.getId());
        assertEquals(ProductType.CARD, productDto.getType());
        assertEquals(cardData, productDto.getData());
    }

    @Test
    void productDto_WithAccountData_ShouldWorkCorrectly() {
        // Given
        ProductDto productDto = new ProductDto();
        AccountData accountData = new AccountData();
        AccountData.AccountDetails details = new AccountData.AccountDetails();
        details.setNumber("1234567890");
        accountData.setAccount(details);

        // When
        productDto.setId("account-id");
        productDto.setType(ProductType.ACCOUNT);
        productDto.setData(accountData);

        // Then
        assertEquals("account-id", productDto.getId());
        assertEquals(ProductType.ACCOUNT, productDto.getType());
        assertEquals(accountData, productDto.getData());
    }
}

