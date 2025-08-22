package az.abb.customer.integration.dto.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalDto {

    private String prefix;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private PlaceOfBirthDto placeOfBirth;
    private String legalGuardian;
    private String sex;
    private IdDto id;
    private ContactDetailsDto contactDetails;
    private AddressDto permanentAddress;
    private AddressDto registrationAddress;
    private AddressDto domicileAddress;
    private String residentStatus;
    private String educationalStatus;
    private String maritalStatus;
    private String accomodationType;
    private String dependants;
    private String nationality;
    private int numberOfOtherDependants;
    private int numberOfChildDependants;

    @JsonProperty("taxID")
    private String taxId;
}
