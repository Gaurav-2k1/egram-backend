package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Scheme;
import in.gram.gov.app.egram_service.dto.request.SchemeRequestDTO;
import in.gram.gov.app.egram_service.dto.response.SchemeResponseDTO;

public class SchemeTransformer {

    public static SchemeResponseDTO toDTO(Scheme scheme) {
        if (scheme == null) {
            return null;
        }
        
        SchemeResponseDTO dto = new SchemeResponseDTO();
        dto.setSchemeId(scheme.getId());
        dto.setTitle(scheme.getTitle());
        dto.setDescription(scheme.getDescription());
        dto.setEligibilityText(scheme.getEligibilityText());
        dto.setDocumentUrls(scheme.getDocumentUrls());
        dto.setStatus(scheme.getStatus());
        dto.setContactPersonName(scheme.getContactPersonName());
        dto.setContactPersonPhone(scheme.getContactPersonPhone());
        dto.setContactPersonEmail(scheme.getContactPersonEmail());
        dto.setBudgetAmount(scheme.getBudgetAmount());
        dto.setBeneficiaryCount(scheme.getBeneficiaryCount());
        dto.setCreatedAt(scheme.getCreatedAt());
        dto.setUpdatedAt(scheme.getUpdatedAt());
        
        return dto;
    }

    public static Scheme toEntity(SchemeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Scheme.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eligibilityText(dto.getEligibilityText())
                .documentUrls(dto.getDocumentUrls())
                .contactPersonName(dto.getContactPersonName())
                .contactPersonPhone(dto.getContactPersonPhone())
                .contactPersonEmail(dto.getContactPersonEmail())
                .budgetAmount(dto.getBudgetAmount())
                .beneficiaryCount(dto.getBeneficiaryCount())
                .build();
    }
}

