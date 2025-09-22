package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductResponseTest {

    @Test
    void productResponse_ShouldCreateWithBuilder() {
        // Given
        String id = "test-id";
        String type = "CARD";
        String number = "1234567890123456";
        String status = "ACTIVE";
        CardData data = new CardData();

        // When
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .type(type)
                .number(number)
                .status(status)
                .data(data)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(type, response.getType());
        assertEquals(number, response.getNumber());
        assertEquals(status, response.getStatus());
        assertEquals(data, response.getData());
    }

    @Test
    void productResponse_ShouldCreateWithMinimalData() {
        // Given
        String id = "test-id";
        String type = "ACCOUNT";

        // When
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .type(type)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(type, response.getType());
        assertNull(response.getNumber());
        assertNull(response.getStatus());
        assertNull(response.getData());
    }

    @Test
    void productResponse_ShouldCreateWithAccountData() {
        // Given
        String id = "account-id";
        String type = "ACCOUNT";
        AccountData data = new AccountData();

        // When
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .type(type)
                .data(data)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(type, response.getType());
        assertEquals(data, response.getData());
    }
}

