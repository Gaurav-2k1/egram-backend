package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.dto.filters.PanchayatFilter;
import in.gram.gov.app.egram_service.dto.request.PanchayatRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatStatsResponseDTO;
import in.gram.gov.app.egram_service.constants.enums.PanchayatStatus;
import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.service.*;
import in.gram.gov.app.egram_service.transformer.PanchayatTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PanchayatFacade {
    private final PanchayatService panchayatService;
    private final UserService userService;
    private final PostService postService;
    private final AnnouncementService announcementService;
    private final SchemeService schemeService;
    private final DocumentService documentService;
    private final GalleryImageService galleryImageService;

    @Transactional
    public PanchayatResponseDTO create(PanchayatRequestDTO request) {
        Panchayat panchayat = PanchayatTransformer.toEntity(request);
        panchayat = panchayatService.create(panchayat);
        return PanchayatTransformer.toDTO(panchayat);
    }

    public PanchayatResponseDTO getById(Long id) {
        Panchayat panchayat = panchayatService.findById(id);
        return PanchayatTransformer.toDTO(panchayat);
    }

    public PanchayatResponseDTO getBySlug(String slug) {
        Panchayat panchayat = panchayatService.findBySlug(slug);
        return PanchayatTransformer.toDTO(panchayat);
    }

    public Page<PanchayatResponseDTO> getAll(PanchayatFilter panchayatFilter) {
        Page<Panchayat> panchayats = panchayatService.findByFilters(panchayatFilter);
        return panchayats.map(PanchayatTransformer::toDTO);
    }

    @Transactional
    public PanchayatResponseDTO update(Long id, PanchayatRequestDTO request) {
        Panchayat panchayat = panchayatService.findById(id);
        PanchayatTransformer.updateEntity(panchayat, request);
        panchayat = panchayatService.update(panchayat);
        return PanchayatTransformer.toDTO(panchayat);
    }

    @Transactional
    public void updateStatus(Long id, PanchayatStatus status) {
        panchayatService.updateStatus(id, status);
    }

    @Transactional
    public void delete(Long id) {
        panchayatService.delete(id);
    }

    public PanchayatStatsResponseDTO getStats(Long id) {
        PanchayatStatsResponseDTO stats = new PanchayatStatsResponseDTO();
        stats.setTotalUsers(userService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        stats.setTotalPosts(postService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        stats.setTotalAnnouncements(announcementService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        stats.setTotalSchemes(schemeService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        stats.setTotalDocuments(documentService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        stats.setTotalGalleryImages(galleryImageService.findByPanchayatId(id, Pageable.unpaged()).getTotalElements());
        return stats;
    }

    public PanchayatResponseDTO getCurrentPanchayat() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            throw new RuntimeException("No tenant context found");
        }
        return getById(tenantId);
    }

    @Transactional
    public PanchayatResponseDTO updateCurrent(PanchayatRequestDTO request) {
        Long tenantId = TenantContext.getTenantId();
        return update(tenantId, request);
    }
}

