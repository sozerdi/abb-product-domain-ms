package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDefinedFieldDto {

    private String fieldName;
    private String fieldValue;
}
