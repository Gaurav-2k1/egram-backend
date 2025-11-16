package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.enums.ActionType;
import in.gram.gov.app.egram_service.domain.entity.AuditLog;
import in.gram.gov.app.egram_service.domain.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public AuditLog create(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    public Page<AuditLog> findByFilters(Long panchayatId, ActionType actionType, 
                                         LocalDateTime startDate, LocalDateTime endDate, 
                                         Pageable pageable) {
        return auditLogRepository.findByFilters(panchayatId, actionType, startDate, endDate, pageable);
    }
}

