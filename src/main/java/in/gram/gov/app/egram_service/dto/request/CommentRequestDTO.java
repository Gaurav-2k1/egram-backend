package in.gram.gov.app.egram_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotBlank(message = "Commenter name is required")
    @Size(max = 100)
    private String commenterName;

    @Email
    private String commenterEmail;

    @NotBlank(message = "Comment body is required")
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    private String bodyText;

    private Long parentCommentId;
}

