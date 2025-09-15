package az.abb.product.dto.productrestriction;

import az.abb.product.enums.PartyType;
import lombok.Builder;

@Builder
public record ProductRestrictionRequest(String productCode, PartyType partyType) {
}
