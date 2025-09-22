package az.abb.product.dto.offlineproduct;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterRequest {
    private List<String> types;
    private Filters filters;
    private Integer limit;
    private Sort sort;
    private Fields fields;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filters {
        private Card card;
        private Account account;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Card {
            public ProductCode productCode;
            public Boolean expired;
            public Boolean visible;
            public Boolean primary;
            public CardNumber cardNumber;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class CardNumber {
                private List<String> in;
                private List<String> notIn;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Account {
            public ProductCode productCode;
            public AccountNumber accountNumber;
            public Boolean visible;
            public Boolean primary;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class AccountNumber {
                private List<String> in;
                private List<String> notIn;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StoredCard {

            private Boolean visible;
            private Boolean primary;

        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCode {
        private List<String> in;
        private List<String> notIn;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sort {
        private String field;
        private String order;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fields {
        private List<String> card;
        private List<String> account;
    }
}
