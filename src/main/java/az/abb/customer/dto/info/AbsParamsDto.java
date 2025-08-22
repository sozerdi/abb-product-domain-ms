package az.abb.customer.dto.info;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsParamsDto {

    private String branch;
    private String element;
    private String location;
    private String expcateg;
    private String nlty;
    private String ccateg;
    private String media;
    private String edvsvo;
    private String cifstat;
    private String chggrp;
    private String miscls;
    private String miscd;
    private List<UserDefinedFieldDto> udf;
}
