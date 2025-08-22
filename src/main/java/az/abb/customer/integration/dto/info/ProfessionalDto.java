package az.abb.customer.integration.dto.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessionalDto {

    private String employmentStatus;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime employedSince;
    private EmployerDto currentEmployer;
    private String currentDesignation;
    private EmployerDto previousEmployer;
    private String previousDesignation;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private Integer totalExperience;
    private String profession;
    private String activityArea;
    private String ownsSalaryCard;
    private String incomeSource;
    private BigDecimal incomeAmount;
    private String customerOfOtherBanks;
    private BigDecimal companySharesRate;
    private String companySharesName;
    private String accountPurpose;
    private String businessActivityCode;
}
