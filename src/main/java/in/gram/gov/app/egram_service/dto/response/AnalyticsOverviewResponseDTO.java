package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

@Data
public class AnalyticsOverviewResponseDTO {
    private Long totalPageViews;
    private Long uniqueVisitors;
    private Long totalPosts;
    private Long totalLikes;
    private Long totalComments;
    private Long totalDocuments;
}

