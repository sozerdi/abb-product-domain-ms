package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfessionalContactDetailsDto {

    private String email;
    private String mobile1;
    private String mobile2;
    private String mobile3;
    private String homeTelephone;
    private String workTelephone;
    private String workExtensionPhoneNumber;
    private String socialNetworkMember;
    private String socialNetworkId;
    private String alternativeEmail;
    private String relativeTelephone;
    private String friendTelephone;
    private String colleagueTelephone;
}
