package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Announcement;
import in.gram.gov.app.egram_service.dto.request.AnnouncementRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AnnouncementResponseDTO;

public class AnnouncementTransformer {

    public static AnnouncementResponseDTO toDTO(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        
        AnnouncementResponseDTO dto = new AnnouncementResponseDTO();
        dto.setAnnouncementId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setBodyText(announcement.getBodyText());
        dto.setAttachments(announcement.getAttachments());
        dto.setStartDate(announcement.getStartDate());
        dto.setEndDate(announcement.getEndDate());
        dto.setPriority(announcement.getPriority());
        dto.setIsActive(announcement.getIsActive());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        
        return dto;
    }

    public static Announcement toEntity(AnnouncementRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Announcement.builder()
                .title(dto.getTitle())
                .bodyText(dto.getBodyText())
                .attachments(dto.getAttachments())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .priority(dto.getPriority() != null ? dto.getPriority() : 0)
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }
}

