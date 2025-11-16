package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.domain.entity.Analytics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    @Query("SELECT a FROM Analytics a WHERE a.panchayat.panchayatId = :panchayatId")
    Page<Analytics> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.panchayat.panchayatId = :panchayatId AND " +
           "a.createdAt >= :startDate AND a.createdAt <= :endDate")
    Long countByPanchayatIdAndDateRange(@Param("panchayatId") Long panchayatId,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(DISTINCT a.sessionId) FROM Analytics a WHERE a.panchayat.panchayatId = :panchayatId AND " +
           "a.createdAt >= :startDate AND a.createdAt <= :endDate")
    Long countUniqueSessionsByPanchayatIdAndDateRange(@Param("panchayatId") Long panchayatId,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);
}

