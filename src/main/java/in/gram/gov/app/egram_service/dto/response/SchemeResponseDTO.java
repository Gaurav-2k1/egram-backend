package in.gram.gov.app.egram_service.dto.response;

import in.gram.gov.app.egram_service.constants.enums.SchemeStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SchemeResponseDTO {
    private Long schemeId;
    private String title;
    private String description;
    private String eligibilityText;
    private List<String> documentUrls;
    private SchemeStatus status;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;
    private BigDecimal budgetAmount;
    private Integer beneficiaryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

