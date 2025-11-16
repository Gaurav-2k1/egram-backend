package in.gram.gov.app.egram_service.dto.request;

import in.gram.gov.app.egram_service.constants.enums.DocumentCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 300)
    private String title;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    private DocumentCategory category;
    private String description;
    private Long fileSize;
    private String mimeType;
}

