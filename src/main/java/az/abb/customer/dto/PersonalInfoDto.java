package az.abb.customer.dto;

import az.abb.customer.integration.dto.info.IdDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
