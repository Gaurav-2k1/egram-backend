package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Announcement;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.request.AnnouncementRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AnnouncementResponseDTO;
import in.gram.gov.app.egram_service.service.AnnouncementService;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.service.UserService;
import in.gram.gov.app.egram_service.transformer.AnnouncementTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnouncementFacade {
    private final AnnouncementService announcementService;
    private final PanchayatService panchayatService;
    private final UserService userService;

    @Transactional
    public AnnouncementResponseDTO create(AnnouncementRequestDTO request, String email) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);
        User author = userService.findByEmail(email);

        Announcement announcement = AnnouncementTransformer.toEntity(request);
        announcement.setPanchayat(panchayat);
        announcement.setCreatedBy(author);

        announcement = announcementService.create(announcement);
        return AnnouncementTransformer.toDTO(announcement);
    }

    public AnnouncementResponseDTO getById(Long id) {
        Announcement announcement = announcementService.findById(id);
        return AnnouncementTransformer.toDTO(announcement);
    }

    public Page<AnnouncementResponseDTO> getAll(Integer page, Integer size) {
        Long tenantId = TenantContext.getTenantId();
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Announcement> announcements = announcementService.findByPanchayatId(tenantId, pageable);
        return announcements.map(AnnouncementTransformer::toDTO);
    }

    @Transactional
    public AnnouncementResponseDTO update(Long id, AnnouncementRequestDTO request) {
        Announcement announcement = announcementService.findById(id);
        
        if (request.getTitle() != null) {
            announcement.setTitle(request.getTitle());
        }
        if (request.getBodyText() != null) {
            announcement.setBodyText(request.getBodyText());
        }
        if (request.getAttachments() != null) {
            announcement.setAttachments(request.getAttachments());
        }
        if (request.getStartDate() != null) {
            announcement.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            announcement.setEndDate(request.getEndDate());
        }
        if (request.getPriority() != null) {
            announcement.setPriority(request.getPriority());
        }
        if (request.getIsActive() != null) {
            announcement.setIsActive(request.getIsActive());
        }
        
        announcement = announcementService.update(announcement);
        return AnnouncementTransformer.toDTO(announcement);
    }

    @Transactional
    public void updateStatus(Long id, Boolean isActive) {
        announcementService.updateStatus(id, isActive);
    }

    @Transactional
    public void delete(Long id) {
        announcementService.delete(id);
    }

    public Page<AnnouncementResponseDTO> getActiveBySlug(String slug, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.DESC, "priority")
                    .and(Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        Page<Announcement> announcements = announcementService.findActiveBySlug(slug, pageable);
        return announcements.map(AnnouncementTransformer::toDTO);
    }
}

