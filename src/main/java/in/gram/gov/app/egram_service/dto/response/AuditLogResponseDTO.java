package in.gram.gov.app.egram_service.dto.response;

import in.gram.gov.app.egram_service.constants.enums.ActionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AuditLogResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long panchayatId;
    private String panchayatName;
    private ActionType actionType;
    private String targetEntityType;
    private Long targetEntityId;
    private Map<String, Object> changes;
    private String ipAddress;
    private String userAgent;
    private String description;
    private LocalDateTime createdAt;
}

