package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.enums.SchemeStatus;
import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.Scheme;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.request.SchemeRequestDTO;
import in.gram.gov.app.egram_service.dto.response.SchemeResponseDTO;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.service.SchemeService;
import in.gram.gov.app.egram_service.service.UserService;
import in.gram.gov.app.egram_service.transformer.SchemeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchemeFacade {
    private final SchemeService schemeService;
    private final PanchayatService panchayatService;
    private final UserService userService;

    @Transactional
    public SchemeResponseDTO create(SchemeRequestDTO request, String email) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);
        User author = userService.findByEmail(email);

        Scheme scheme = SchemeTransformer.toEntity(request);
        scheme.setPanchayat(panchayat);
        scheme.setCreatedBy(author);
        if (scheme.getStatus() == null) {
            scheme.setStatus(SchemeStatus.ACTIVE);
        }

        scheme = schemeService.create(scheme);
        return SchemeTransformer.toDTO(scheme);
    }

    public SchemeResponseDTO getById(Long id) {
        Scheme scheme = schemeService.findById(id);
        return SchemeTransformer.toDTO(scheme);
    }

    public Page<SchemeResponseDTO> getAll(Integer page, Integer size) {
        Long tenantId = TenantContext.getTenantId();
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Scheme> schemes = schemeService.findByPanchayatId(tenantId, pageable);
        return schemes.map(SchemeTransformer::toDTO);
    }

    @Transactional
    public SchemeResponseDTO update(Long id, SchemeRequestDTO request) {
        Scheme scheme = schemeService.findById(id);
        
        if (request.getTitle() != null) {
            scheme.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            scheme.setDescription(request.getDescription());
        }
        if (request.getEligibilityText() != null) {
            scheme.setEligibilityText(request.getEligibilityText());
        }
        if (request.getDocumentUrls() != null) {
            scheme.setDocumentUrls(request.getDocumentUrls());
        }
        if (request.getContactPersonName() != null) {
            scheme.setContactPersonName(request.getContactPersonName());
        }
        if (request.getContactPersonPhone() != null) {
            scheme.setContactPersonPhone(request.getContactPersonPhone());
        }
        if (request.getContactPersonEmail() != null) {
            scheme.setContactPersonEmail(request.getContactPersonEmail());
        }
        if (request.getBudgetAmount() != null) {
            scheme.setBudgetAmount(request.getBudgetAmount());
        }
        if (request.getBeneficiaryCount() != null) {
            scheme.setBeneficiaryCount(request.getBeneficiaryCount());
        }
        
        scheme = schemeService.update(scheme);
        return SchemeTransformer.toDTO(scheme);
    }

    @Transactional
    public void updateStatus(Long id, SchemeStatus status) {
        schemeService.updateStatus(id, status);
    }

    @Transactional
    public void delete(Long id) {
        schemeService.delete(id);
    }

    public Page<SchemeResponseDTO> getActiveBySlug(String slug, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Scheme> schemes = schemeService.findActiveBySlug(slug, pageable);
        return schemes.map(SchemeTransformer::toDTO);
    }
}

