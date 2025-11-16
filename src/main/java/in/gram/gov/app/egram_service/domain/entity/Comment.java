package in.gram.gov.app.egram_service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments",
        indexes = {
                @Index(name = "idx_comment_post", columnList = "post_id"),
                @Index(name = "idx_comment_parent", columnList = "parent_comment_id"),
                @Index(name = "idx_comment_approved", columnList = "approved_flag, created_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;

    @NotBlank(message = "Commenter name is required")
    @Size(max = 100)
    @Column(name = "commenter_name", nullable = false, length = 100)
    private String commenterName;

    @Email
    @Column(name = "commenter_email", length = 100)
    private String commenterEmail;

    @NotBlank(message = "Comment body is required")
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    @Column(name = "body_text", columnDefinition = "TEXT", nullable = false)
    private String bodyText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", foreignKey = @ForeignKey(name = "fk_comment_parent"))
    private Comment parentComment;

    @Column(name = "approved_flag", nullable = false)
    @Builder.Default
    private Boolean approvedFlag = false;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    // Relationships
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> replies = new ArrayList<>();
}