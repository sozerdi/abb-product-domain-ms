package az.abb.customer.integration.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String index;
    private String country;
    private String district;

}
