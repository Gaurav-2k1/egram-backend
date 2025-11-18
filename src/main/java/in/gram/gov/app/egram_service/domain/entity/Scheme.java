package in.gram.gov.app.egram_service.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import in.gram.gov.app.egram_service.constants.enums.SchemeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "schemes",
        indexes = {
                @Index(name = "idx_scheme_panchayat", columnList = "panchayat_id"),
                @Index(name = "idx_scheme_status", columnList = "status"),
                @Index(name = "idx_scheme_created_by", columnList = "created_by_user_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scheme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "panchayat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_scheme_panchayat"))
    private Panchayat panchayat;

    @NotBlank(message = "Title is required")
    @Size(max = 300)
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @NotBlank(message = "Description is required")
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "eligibility_text", columnDefinition = "TEXT")
    private String eligibilityText;

    @Type(JsonType.class)
    @Column(name = "document_urls", columnDefinition = "jsonb")
    private List<String> documentUrls;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private SchemeStatus status = SchemeStatus.ACTIVE;

    @Size(max = 100)
    @Column(name = "contact_person_name", length = 100)
    private String contactPersonName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(name = "contact_person_phone", length = 15)
    private String contactPersonPhone;

    @Email
    @Column(name = "contact_person_email", length = 100)
    private String contactPersonEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_scheme_creator"))
    private User createdBy;

    @Column(name = "budget_amount")
    private java.math.BigDecimal budgetAmount;

    @Column(name = "beneficiary_count")
    private Integer beneficiaryCount;
}
