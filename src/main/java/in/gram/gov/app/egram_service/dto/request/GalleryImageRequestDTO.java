package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GalleryImageRequestDTO {
    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @Size(max = 500)
    private String caption;

    private String tags;
    private Long albumId;
    private Integer displayOrder;
}

