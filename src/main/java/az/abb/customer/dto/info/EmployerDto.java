package az.abb.customer.dto.info;

import az.abb.customer.dto.AddressDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployerDto {

    private String name;
    private AddressDto address;
    private ProfessionalContactDetailsDto contact;
}
