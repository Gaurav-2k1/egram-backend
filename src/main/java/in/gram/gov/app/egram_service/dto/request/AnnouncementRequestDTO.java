package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnnouncementRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 300)
    private String title;

    @NotBlank(message = "Body text is required")
    private String bodyText;

    private List<String> attachments;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private Boolean isActive;
}

