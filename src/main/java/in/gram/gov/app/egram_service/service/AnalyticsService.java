package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.domain.entity.Analytics;
import in.gram.gov.app.egram_service.domain.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    @Transactional
    public Analytics create(Analytics analytics) {
        return analyticsRepository.save(analytics);
    }

    public Page<Analytics> findByPanchayatId(Long panchayatId, Pageable pageable) {
        return analyticsRepository.findByPanchayatId(panchayatId, pageable);
    }

    public Long countByPanchayatIdAndDateRange(Long panchayatId, LocalDateTime startDate, LocalDateTime endDate) {
        return analyticsRepository.countByPanchayatIdAndDateRange(panchayatId, startDate, endDate);
    }

    public Long countUniqueSessionsByPanchayatIdAndDateRange(Long panchayatId, LocalDateTime startDate, LocalDateTime endDate) {
        return analyticsRepository.countUniqueSessionsByPanchayatIdAndDateRange(panchayatId, startDate, endDate);
    }
}

