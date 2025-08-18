package az.abb.customer.dto;

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
public class CustomerDetailsDto {
    CustomerBaseDto base;
    PersonalInfoDto personal;
    JsonNode customAttributes;
}
