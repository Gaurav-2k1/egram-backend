package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GalleryImageResponseDTO {
    private Long imageId;
    private String imageUrl;
    private String caption;
    private String tags;
    private Long albumId;
    private String albumName;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

