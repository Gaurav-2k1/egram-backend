package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

@Data
public class PanchayatStatsResponseDTO {
    private Long totalUsers;
    private Long totalPosts;
    private Long totalAnnouncements;
    private Long totalSchemes;
    private Long totalDocuments;
    private Long totalGalleryImages;
}

