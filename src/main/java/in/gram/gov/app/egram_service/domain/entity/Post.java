package in.gram.gov.app.egram_service.domain.entity;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts",
        indexes = {
                @Index(name = "idx_post_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_post_author", columnList = "author_user_id"),
                @Index(name = "idx_post_status_published", columnList = "status, published_at"),
                @Index(name = "idx_post_panchayat_status", columnList = "panchayat_id, status, published_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_panchayat"))
    private Panchayat panchayat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_author"))
    private User author;

    @Size(max = 300, message = "Title must not exceed 300 characters")
    @Column(name = "title", length = 300)
    private String title;

    @NotBlank(message = "Body text is required")
    @Column(name = "body_text", columnDefinition = "TEXT", nullable = false)
    private String bodyText;

    @Column(name = "media_url", length = 500)
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PostStatus status = PostStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "view_count")
    @Builder.Default
    private Long viewCount = 0L;

    // Relationships
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Like> likes = new ArrayList<>();

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        if (this.status == PostStatus.PUBLISHED && this.publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
    }
}
