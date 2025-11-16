
// ============================================
// 8. GalleryImage Entity
// ============================================
package in.gram.gov.app.egram_service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "gallery_images",
        indexes = {
                @Index(name = "idx_gallery_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_gallery_album", columnList = "album_id"),
                @Index(name = "idx_gallery_uploaded_by", columnList = "uploaded_by_user_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_gallery_panchayat"))
    private Panchayat panchayat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", foreignKey = @ForeignKey(name = "fk_gallery_album"))
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploaded_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_gallery_uploader"))
    private User uploadedBy;

    @NotBlank(message = "Image URL is required")
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Size(max = 500)
    @Column(name = "caption", length = 500)
    private String caption;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "display_order")
    private Integer displayOrder;
}