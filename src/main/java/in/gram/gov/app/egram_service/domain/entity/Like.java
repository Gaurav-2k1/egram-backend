
// ============================================
// 6. Like Entity
// ============================================
package in.gram.gov.app.egram_service.domain.entity;

import in.gram.gov.app.egram_service.constants.enums.ReactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes",
        indexes = {
                @Index(name = "idx_like_post", columnList = "post_id"),
                @Index(name = "idx_like_user", columnList = "user_id"),
                @Index(name = "idx_like_visitor", columnList = "visitor_identifier")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_like_post_user", columnNames = {"post_id", "user_id"}),
                @UniqueConstraint(name = "uk_like_post_visitor", columnNames = {"post_id", "visitor_identifier"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "fk_like_post"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_like_user"))
    private User user;

    @Column(name = "visitor_identifier", length = 100)
    private String visitorIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false, length = 20)
    @Builder.Default
    private ReactionType reactionType = ReactionType.LIKE;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
