package in.gram.gov.app.egram_service.domain.entity;


import in.gram.gov.app.egram_service.constants.enums.DocumentCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "documents",
        indexes = {
                @Index(name = "idx_document_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_document_category", columnList = "category"),
                @Index(name = "idx_document_uploaded_by", columnList = "uploaded_by_user_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_document_panchayat"))
    private Panchayat panchayat;

    @NotBlank(message = "Title is required")
    @Size(max = 300)
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @NotBlank(message = "File URL is required")
    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    @Builder.Default
    private DocumentCategory category = DocumentCategory.OTHER;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uploaded_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_document_uploader"))
    private User uploadedBy;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Column(name = "download_count")
    @Builder.Default
    private Long downloadCount = 0L;
}