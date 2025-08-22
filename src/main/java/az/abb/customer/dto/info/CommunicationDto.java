package az.abb.customer.dto.info;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationDto {

    private List<CustomerAddressDto> customerAddresses;
    private List<CustomerContactDto> customerContacts;
    private List<CustomerRelationDto> customerRelations;
}
