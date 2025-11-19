package in.gram.gov.app.egram_service.controller.admin;

import in.gram.gov.app.egram_service.constants.enums.ActionType;
import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.filters.AuditFilter;
import in.gram.gov.app.egram_service.dto.response.AuditLogResponseDTO;
import in.gram.gov.app.egram_service.facade.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/admin/audit-logs")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminAuditLogController {
    private final AdminFacade adminFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponseDTO>>> getAuditLogs(
            AuditFilter auditFilter) {
        PagedResponse<AuditLogResponseDTO> response = PagedResponse.of(
                adminFacade.getAuditLogs(auditFilter));
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

