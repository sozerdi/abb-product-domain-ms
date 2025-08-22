package az.abb.customer.integration.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseDto {

    private String cuid;
    private String customerNumber;
    private String customerType;
    private String name;
    private String isFrozen;
    private String classification;
    private String taxId;

}
