package az.abb.product.dto.offlineproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AccountDataTest {

    @Test
    void accountData_ShouldCreateWithDefaultConstructor() {
        // When
        AccountData accountData = new AccountData();

        // Then
        assertNotNull(accountData);
        assertNull(accountData.getAccount());
        assertNull(accountData.getDescription());
        assertNull(accountData.getCurrency());
    }

    @Test
    void accountData_ShouldSetAndGetAccount() {
        // Given
        AccountData accountData = new AccountData();
        AccountData.AccountDetails details = new AccountData.AccountDetails();
        details.setNumber("1234567890");
        details.setCategory("SAVINGS");

        // When
        accountData.setAccount(details);
        accountData.setDescription("Test Account");
        accountData.setCurrency("USD");

        // Then
        assertEquals(details, accountData.getAccount());
        assertEquals("1234567890", accountData.getAccount().getNumber());
        assertEquals("SAVINGS", accountData.getAccount().getCategory());
        assertEquals("Test Account", accountData.getDescription());
        assertEquals("USD", accountData.getCurrency());
    }

    @Test
    void accountDetails_ShouldCreateWithDefaultConstructor() {
        // When
        AccountData.AccountDetails details = new AccountData.AccountDetails();

        // Then
        assertNotNull(details);
        assertNull(details.getNumber());
        assertNull(details.getCategory());
        assertNull(details.getBookAccount());
    }

    @Test
    void accountDetails_ShouldSetAndGetProperties() {
        // Given
        AccountData.AccountDetails details = new AccountData.AccountDetails();
        String number = "1234567890";
        String category = "SAVINGS";
        String bookAccount = "BOOK123";

        // When
        details.setNumber(number);
        details.setCategory(category);
        details.setBookAccount(bookAccount);

        // Then
        assertEquals(number, details.getNumber());
        assertEquals(category, details.getCategory());
        assertEquals(bookAccount, details.getBookAccount());
    }

    @Test
    void accountData_WithBuilder_ShouldCreateCorrectly() {
        // Given
        AccountData.AccountDetails details = new AccountData.AccountDetails();
        details.setNumber("1234567890");
        details.setCategory("SAVINGS");

        // When
        AccountData accountData = new AccountData();
        accountData.setAccount(details);
        accountData.setDescription("Test Account");

        // Then
        assertNotNull(accountData);
        assertEquals(details, accountData.getAccount());
        assertEquals("1234567890", accountData.getAccount().getNumber());
        assertEquals("SAVINGS", accountData.getAccount().getCategory());
        assertEquals("Test Account", accountData.getDescription());
    }
}
