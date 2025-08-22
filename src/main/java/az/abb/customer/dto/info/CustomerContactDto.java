package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerContactDto {

    private String tag;
    private String value;
    private String description;
    private String isActive;
    private String isDefault;
    private String telephoneIsd;
    private String contactId;
    private String status;
}
