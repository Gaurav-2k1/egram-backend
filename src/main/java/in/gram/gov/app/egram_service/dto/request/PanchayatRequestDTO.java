package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PanchayatRequestDTO {
    @NotBlank(message = "Panchayat name is required")
    @Size(max = 200)
    private String panchayatName;

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    @Size(max = 100)
    private String slug;

    @NotBlank(message = "District is required")
    @Size(max = 100)
    private String district;

    @NotBlank(message = "State is required")
    @Size(max = 100)
    private String state;

    private String address;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String contactPhone;

    @Email
    private String contactEmail;

    private String description;
    private String aboutText;
    private String officeAddress;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String officePhone;

    @Email
    private String officeEmail;

    private String mapCoordinates;
    private String officeHours;
}

