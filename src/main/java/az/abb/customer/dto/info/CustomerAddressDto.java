package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerAddressDto {

    private String addressId;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String city;
    private String index;
    private String district;
    private String country;
    private String region;
    private String addressTag;
    private String village;
    private String originalAddressLine;
    private String isActive;
    private String isDefault;
}
