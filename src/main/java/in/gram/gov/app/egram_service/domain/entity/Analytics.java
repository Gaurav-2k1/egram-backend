package in.gram.gov.app.egram_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analytics",
        indexes = {
                @Index(name = "idx_analytics_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_analytics_created_at", columnList = "created_at"),
                @Index(name = "idx_analytics_panchayat_date", columnList = "panchayat_id, created_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_view_id")
    private Long pageViewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_analytics_panchayat"))
    private Panchayat panchayat;

    @Column(name = "visitor_ip", length = 45)
    private String visitorIp;

    @Column(name = "page_url", length = 500)
    private String pageUrl;

    @Column(name = "referrer_url", length = 500)
    private String referrerUrl;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "device_type", length = 20)
    private String deviceType;

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
