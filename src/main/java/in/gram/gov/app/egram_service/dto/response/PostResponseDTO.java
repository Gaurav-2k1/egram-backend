package in.gram.gov.app.egram_service.dto.response;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Long postId;
    private String title;
    private String bodyText;
    private String mediaUrl;
    private PostStatus status;
    private LocalDateTime publishedAt;
    private Long viewCount;
    private Long likesCount;
    private Long commentsCount;
    private Long panchayatId;
    private Long authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

