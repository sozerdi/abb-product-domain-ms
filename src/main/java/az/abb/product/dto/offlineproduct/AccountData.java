package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
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
public class AccountData {

    private String description;
    private Customer customer;
    private AccountDetails account;
    private Branch branch;
    private BigDecimal balance;
    private BigDecimal lcyCurrBalance;
    private BigDecimal acyCurrBalance;
    private String currency;
    private String status;
    private String openDate;
    private String closeDate;
    private String location;
    @JsonProperty("expCat")
    private String exposureCategory;
    private List<UserDefinedFields> userDefinedFields;
    @JsonProperty("accType")
    private String accountType;
    private TodLimit todLimit;
    private Boolean creditIsAllowed;
    private Boolean debitIsAllowed;
    private Boolean blocked;
    private Boolean frozen;
    private Boolean stopPay;
    private Boolean disposal;
    private String recordStatus;
    private String voen;
    private String bankCode;
    private Bank bank;
    private Boolean clAccount;
    private InterestAccount interestAccount;
    @JsonProperty("class")
    private String accountClass;

    @Getter
    @Setter
    public static class Customer {
        private String name;
        private String cif;
        private String passportId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDetails {
        private String number;
        private String category;
        private String bookAccount;
        private String iban;
        private String alternativeNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Branch {
        private String code;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodLimit {
        private String startDate;
        private String endDate;
        private String interest;
        private BigDecimal amount;
        private BigDecimal totalAmount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterestAccount {
        private Boolean active;
        private BigDecimal dailyInterestAmount;
        private BigDecimal minBalanceLimit;
        private BigDecimal maxBalanceLimit;
        private BigDecimal interestRate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDefinedFields {

        private CustomUserField field;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CustomUserField {
            private String fieldName;
            private String fieldValue;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bank {
        private String code;
        private String name;
        private String iban;
        @JsonAlias({"swift", "swfit"})
        private String swift;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Fields {
        public static final String RECORD_STATUS = "data.recordStatus";
        public static final String ACCOUNT_CLASS = "data.class";
    }
}
