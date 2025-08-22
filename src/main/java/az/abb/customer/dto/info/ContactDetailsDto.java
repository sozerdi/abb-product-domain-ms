package az.abb.customer.dto.info;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ContactDetailsDto extends ProfessionalContactDetailsDto{

    private String telephoneIsd;
    private String mobileIsd;
}
