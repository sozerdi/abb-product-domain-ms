package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Locale;

public enum ProductType {
    CARD,
    ACCOUNT,
    LOAN,
    DEPOSIT;

    @Override
    public String toString() {
        return name();
    }

    @JsonCreator
    public static ProductType fromString(String v) {
        return v == null ? null : ProductType.valueOf(v.toUpperCase(Locale.ROOT));
    }
}

