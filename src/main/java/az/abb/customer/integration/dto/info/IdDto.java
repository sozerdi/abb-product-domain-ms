package az.abb.customer.integration.dto.info;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IdDto {

    private String idNumber;
    private String personalIdType;
    private String issuingDate;
    private String maturityDate;
    private String issuedByAuthority;
    private String pinCode;
}
