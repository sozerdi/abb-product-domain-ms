package az.abb.customer.integration.dto.info;

import az.abb.customer.dto.CustomerBaseDto;
import az.abb.customer.dto.PersonalInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDetailsIntegrationDto {
    CustomerBaseDto base;
    PersonalInfoDto personal;
    JsonNode customAttributes;
}
