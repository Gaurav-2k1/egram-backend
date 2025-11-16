package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlbumResponseDTO {
    private Long albumId;
    private String albumName;
    private String description;
    private String coverImageUrl;
    private Long imageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

