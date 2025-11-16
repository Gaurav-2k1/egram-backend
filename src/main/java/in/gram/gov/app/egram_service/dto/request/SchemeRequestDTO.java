package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SchemeRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 300)
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String eligibilityText;
    private List<String> documentUrls;

    @Size(max = 100)
    private String contactPersonName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String contactPersonPhone;

    @Email
    private String contactPersonEmail;

    private BigDecimal budgetAmount;
    private Integer beneficiaryCount;
}

