package in.gram.gov.app.egram_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponseDTO {
    private Long commentId;
    private String commenterName;
    private String commenterEmail;
    private String bodyText;
    private Boolean approvedFlag;
    private Long postId;
    private Long parentCommentId;
    private List<CommentResponseDTO> replies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

