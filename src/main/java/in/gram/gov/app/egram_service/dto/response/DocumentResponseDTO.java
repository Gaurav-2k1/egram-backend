package in.gram.gov.app.egram_service.dto.response;

import in.gram.gov.app.egram_service.constants.enums.DocumentCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponseDTO {
    private Long documentId;
    private String title;
    private String fileUrl;
    private DocumentCategory category;
    private String description;
    private Long fileSize;
    private String mimeType;
    private Long downloadCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

