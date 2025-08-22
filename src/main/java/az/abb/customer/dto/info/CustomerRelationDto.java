package az.abb.customer.dto.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRelationDto {

    private String contractRef;
    private String tag;
    private String fullName;
    private String customerNumber;
}
