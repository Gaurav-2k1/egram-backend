
// ============================================
// 9. Announcement Entity
// ============================================
package in.gram.gov.app.egram_service.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "announcements",
        indexes = {
                @Index(name = "idx_announcement_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_announcement_dates", columnList = "start_date, end_date"),
                @Index(name = "idx_announcement_created_by", columnList = "created_by_user_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_announcement_panchayat"))
    private Panchayat panchayat;

    @NotBlank(message = "Title is required")
    @Size(max = 300)
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @NotBlank(message = "Body text is required")
    @Column(name = "body_text", columnDefinition = "TEXT", nullable = false)
    private String bodyText;

    @Type(JsonType.class)
    @Column(name = "attachments", columnDefinition = "jsonb")
    private List<String> attachments;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_announcement_creator"))
    private User createdBy;

    @Column(name = "priority")
    @Builder.Default
    private Integer priority = 0;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}