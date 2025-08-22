package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContactDetailsDto extends ProfessionalContactDetailsDto{

    private String telephoneIsd;
    private String mobileIsd;
}
