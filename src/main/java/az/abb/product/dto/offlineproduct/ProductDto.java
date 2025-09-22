package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto<T> {
    private String id;
    private ProductType type;
    private Integer position;
    private Boolean visible;
    private Boolean primary;
    private String alias;
    private UUID userId;
    private Preference preferences;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("deleted_at")
    private Instant deletedAt;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = CardData.class, name = "CARD"),
            @JsonSubTypes.Type(value = AccountData.class, name = "ACCOUNT"),
            @JsonSubTypes.Type(value = StoredCardData.class, name = "STORED_CARD")
    })
    private T data;

    public BigDecimal getBalance() {
        Object data = this.getData();
        if (data instanceof CardData cd) {
            return cd.getBalance();
        }
        if (data instanceof AccountData ad) {
            return ad.getBalance();
        }
        if (data instanceof StoredCardData scd) {
            return null; // StoredCard doesn't have balance
        }
        return null;
    }

    public String getCurrency() {
        Object data = this.getData();
        if (data instanceof CardData cd) {
            return cd.getCurrency();
        }
        if (data instanceof AccountData ad) {
            return ad.getCurrency();
        }
        if (data instanceof StoredCardData scd) {
            return null; // StoredCard doesn't have currency
        }
        return null;
    }

    public String getProductCode() {
        Object data = this.getData();
        if (data instanceof CardData cd) {
            return Optional.ofNullable(cd.getProduct().getProductCode1()).orElse(cd.getProduct().getProductCode2());
        }
        if (data instanceof StoredCardData scd) {
            return null; // StoredCard doesn't have product code
        }
        return null;
    }

    public BigDecimal getAvailableBalance() {
        Object data = this.getData();
        if (data instanceof CardData cd) {
            return cd.getAvailableBalance();
        }
        if (data instanceof AccountData ad) {
            return ad.getBalance(); // For accounts, use balance as available balance
        }
        if (data instanceof StoredCardData scd) {
            return null; // StoredCard doesn't have available balance
        }
        return null;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Preference {
        private String alias;
        private Boolean visible;
        private Integer position;
        private Boolean primary;
    }
}
