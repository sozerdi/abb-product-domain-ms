package az.abb.customer.dto.info;

import java.util.List;
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
    private BaseDto base;
    private PersonalDto personal;
    private ProfessionalDto professional;
    private AbsParamsDto absParams;
    private List<UserDefinedFieldDto> userDefinedFields;
    private CommunicationDto communication;
}
