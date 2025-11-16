package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnnouncementResponseDTO {
    private Long announcementId;
    private String title;
    private String bodyText;
    private List<String> attachments;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

