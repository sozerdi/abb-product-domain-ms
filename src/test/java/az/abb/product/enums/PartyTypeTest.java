package az.abb.product.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PartyTypeTest {

    @Test
    void partyType_ShouldHaveCorrectValues() {
        // Then
        assertEquals("SENDER", PartyType.SENDER.name());
        assertEquals("RECEIVER", PartyType.RECEIVER.name());
    }

    @Test
    void partyType_ShouldBeAccessible() {
        // When
        PartyType sender = PartyType.SENDER;
        PartyType receiver = PartyType.RECEIVER;

        // Then
        assertNotNull(sender);
        assertNotNull(receiver);
    }
}

