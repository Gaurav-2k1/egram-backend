package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.AuditLog;
import in.gram.gov.app.egram_service.dto.response.AuditLogResponseDTO;

public class AuditLogTransformer {

    public static AuditLogResponseDTO toDTO(AuditLog auditLog) {
        if (auditLog == null) {
            return null;
        }
        
        AuditLogResponseDTO dto = new AuditLogResponseDTO();
        dto.setId(auditLog.getId());
        
        if (auditLog.getUser() != null) {
            dto.setUserId(auditLog.getUser().getId());
            dto.setUserName(auditLog.getUser().getName());
        }
        
        if (auditLog.getPanchayat() != null) {
            dto.setPanchayatId(auditLog.getPanchayat().getId());
            dto.setPanchayatName(auditLog.getPanchayat().getPanchayatName());
        }
        
        dto.setActionType(auditLog.getActionType());
        dto.setTargetEntityType(auditLog.getTargetEntityType());
        dto.setTargetEntityId(auditLog.getTargetEntityId());
        dto.setChanges(auditLog.getChanges());
        dto.setIpAddress(auditLog.getIpAddress());
        dto.setUserAgent(auditLog.getUserAgent());
        dto.setDescription(auditLog.getDescription());
        dto.setCreatedAt(auditLog.getCreatedAt());
        
        return dto;
    }
}

