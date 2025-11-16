package in.gram.gov.app.egram_service.constants.aspect;

import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.AuditLog;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.service.AuditLogService;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLoggingAspect {
    private final AuditLogService auditLogService;
    private final UserService userService;
    private final PanchayatService panchayatService;

    @AfterReturning(pointcut = "@annotation(in.gram.gov.app.egram_service.constants.aspect.Auditable)", returning = "result")
    public void logAudit(JoinPoint joinPoint, Auditable auditable, Object result) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return;
            }

            String email = authentication.getName();
            User user = userService.findByEmailOrNull(email);
            if (user == null) {
                return;
            }

            Long panchayatId = TenantContext.getTenantId();
            Panchayat panchayat = panchayatId != null ? panchayatService.findById(panchayatId) : null;

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            Map<String, Object> changes = new HashMap<>();
            if (result != null) {
                changes.put("result", result.toString());
            }

            AuditLog auditLog = AuditLog.builder()
                    .user(user)
                    .panchayat(panchayat)
                    .actionType(auditable.actionType())
                    .targetEntityType(auditable.entityType())
                    .targetEntityId(extractEntityId(result))
                    .changes(changes)
                    .ipAddress(getClientIpAddress(request))
                    .userAgent(request.getHeader("User-Agent"))
                    .description(auditable.description())
                    .build();

            auditLogService.create(auditLog);
        } catch (Exception e) {
            // Log error but don't fail the operation
            System.err.println("Error creating audit log: " + e.getMessage());
        }
    }

    private Long extractEntityId(Object result) {
        if (result == null) {
            return null;
        }
        try {
            // Try to extract ID from response DTOs
            java.lang.reflect.Method getIdMethod = result.getClass().getMethod("getId");
            Object id = getIdMethod.invoke(result);
            if (id instanceof Long) {
                return (Long) id;
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

