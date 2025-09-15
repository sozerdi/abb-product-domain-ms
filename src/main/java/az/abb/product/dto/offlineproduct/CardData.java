package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardData {
    private String number;
    private String expiryDate;
    private Boolean expired;
    private String network;
    private String bin;
    private String cardType;
    private String usageType;
    private String status;
    private String statusCode;
    private String mobileStatus;
    private String securityName;
    private String main;

    private Product product;
    private Customer customer;
    private Account account;
    private Contract contract;

    private String regNumber;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private BigDecimal blockedAmount;
    private String currency;
    private BigDecimal creditLimit;
    @JsonProperty("default")
    private Boolean isDefault;
    private Features features;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private String productCode1;
        private String productCode2;
        private String accountScheme;
        private String contractSubType;
        private String servicePack;
        private String productName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        private String name;
        private String cif;
        private String passportId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Account {
        private String number;
        @JsonProperty("class")
        private String accountClass;
        private String name;
        private String iban;
        private Branch branch;

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Branch {
            private String code;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Features {
        private Boolean pinEraseEligible;
        @JsonProperty("avansEligible")
        private Boolean avansEligible;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contract {
        private String number;
    }
}
