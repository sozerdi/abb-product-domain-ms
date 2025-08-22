package az.abb.customer.integration.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDefinedFieldDto {

    private String fieldName;
    private String fieldValue;
}
