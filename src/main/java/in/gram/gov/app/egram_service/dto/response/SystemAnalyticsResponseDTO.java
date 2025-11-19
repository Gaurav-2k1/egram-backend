package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

@Data
public class SystemAnalyticsResponseDTO {
    private Long totalPanchayats;
    private Long activePanchayats;
    private Long totalUsers;
    private Long totalPosts;
    private Long totalSchemes;
    private Long totalAnnouncements;
    private Long totalDocuments;
    private Long totalGalleryImages;
}

