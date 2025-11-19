package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.enums.ActionType;
import in.gram.gov.app.egram_service.domain.entity.AuditLog;
import in.gram.gov.app.egram_service.domain.entity.Post;
import in.gram.gov.app.egram_service.domain.repository.AuditLogRepository;
import in.gram.gov.app.egram_service.dto.filters.AuditFilter;
import in.gram.gov.app.egram_service.dto.filters.PostFilter;
import in.gram.gov.app.egram_service.utility.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<AuditLog> findByFilters(AuditFilter auditFilter) {

        Pageable pageable = auditFilter.createPageable(auditFilter);
        Specification<AuditLog> auditSpecification = buildSpecification(auditFilter);

        return auditLogRepository.findAll(auditSpecification, pageable);
    }

    public Specification<AuditLog> buildSpecification(AuditFilter filter) {
        return SpecificationBuilder.<AuditLog>builder()
                .equalTo("panchayat.id", filter.getPanchayatId())
                .equalTo("actionType", filter.getActionType())
                .dateTimeRange("createdAt",
                        filter.getStartDate() != null ? filter.getStartDate() : null,
                        filter.getEndDate() != null ? filter.getEndDate() : null)
                .build();
    }


}

