package az.abb.product.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockDataRepository {

    private static final String DEFAULT_PATH = "mock/mock-data.json";

    private final List<CardSeed> cards;
    private final List<AccountSeed> accounts;

    public MockDataRepository() {
        List<CardSeed> cardList = Collections.emptyList();
        List<AccountSeed> accountList = Collections.emptyList();
        try (InputStream is = new ClassPathResource(DEFAULT_PATH).getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            Root root = mapper.readValue(is, Root.class);
            if (root != null) {
                cardList = root.getCards() != null ? root.getCards() : Collections.emptyList();
                accountList = root.getAccounts() != null ? root.getAccounts() : Collections.emptyList();
            }
            log.info("Loaded mock data from {}: {} cards, {} accounts", 
                    DEFAULT_PATH, cardList.size(), accountList.size());
        } catch (IOException e) {
            log.warn("Mock data file not found or unreadable: {}. Falling back to code-defined seeds.", DEFAULT_PATH);
        }
        this.cards = cardList;
        this.accounts = accountList;
    }

    public List<CardSeed> getCards() {
        return cards;
    }

    public List<AccountSeed> getAccounts() {
        return accounts;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Root {
        private List<CardSeed> cards;
        private List<AccountSeed> accounts;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardSeed {
        private UUID userId;
        private String number;
        private String alias;
        private String cif;
        private String contractNumber;
        private String cardNetwork;
        private String description;
        private String currency;
        private String expiryDate;
        private String cardStatus;
        private String cardType;
        private Boolean pinEraseEligible;
        private String accountClass;
        private String accountNumber;
        private Boolean eligibleForAvans;
        private String usageType;
        private String pin;
        private String productCode1;
        private Double availableBalance;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccountSeed {
        private UUID userId;
        private String cif;
        private String description;
        private String currency;
        private String status;
        private String accountClass;
        private String accountNumber;
        private String iban;
        private String pin;
    }


}
