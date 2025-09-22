package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class IdentifierOptionsTest {

    @Test
    void identifierOptions_ShouldCreateWithBuilder() {
        // Given
        String pin = "1234";
        Set<String> cifs = Set.of("CIF123", "CIF456");
        UUID userId = UUID.randomUUID();

        // When
        IdentifierOptions options = IdentifierOptions.builder()
                .pin(pin)
                .cifs(cifs)
                .userId(userId)
                .build();

        // Then
        assertNotNull(options);
        assertEquals(pin, options.getPin());
        assertEquals(cifs, options.getCifs());
        assertEquals(userId, options.getUserId());
    }

    @Test
    void identifierOptions_ShouldCreateWithPartialData() {
        // Given
        String pin = "1234";
        UUID userId = UUID.randomUUID();

        // When
        IdentifierOptions options = IdentifierOptions.builder()
                .pin(pin)
                .userId(userId)
                .build();

        // Then
        assertNotNull(options);
        assertEquals(pin, options.getPin());
        assertNull(options.getCifs());
        assertEquals(userId, options.getUserId());
    }

    @Test
    void identifierOptions_ShouldCreateWithEmptyCifs() {
        // Given
        String pin = "1234";
        Set<String> cifs = Set.of();
        UUID userId = UUID.randomUUID();

        // When
        IdentifierOptions options = IdentifierOptions.builder()
                .pin(pin)
                .cifs(cifs)
                .userId(userId)
                .build();

        // Then
        assertNotNull(options);
        assertEquals(pin, options.getPin());
        assertEquals(cifs, options.getCifs());
        assertEquals(userId, options.getUserId());
    }

    @Test
    void identifierOptions_ShouldCreateWithNullValues() {
        // When
        IdentifierOptions options = IdentifierOptions.builder()
                .pin(null)
                .cifs(null)
                .userId(null)
                .build();

        // Then
        assertNotNull(options);
        assertNull(options.getPin());
        assertNull(options.getCifs());
        assertNull(options.getUserId());
    }
}

