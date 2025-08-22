package az.abb.customer.dto;

import az.abb.customer.dto.info.IdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private AddressDto registrationAddress;
    private IdDto identification;
}
