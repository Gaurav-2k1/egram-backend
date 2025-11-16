package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.constants.enums.SchemeStatus;
import in.gram.gov.app.egram_service.domain.entity.Scheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    @Query("SELECT s FROM Scheme s WHERE s.panchayat.panchayatId = :panchayatId")
    Page<Scheme> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);
    
    @Query("SELECT s FROM Scheme s WHERE s.panchayat.panchayatId = :panchayatId AND s.status = :status")
    Page<Scheme> findByPanchayatIdAndStatus(@Param("panchayatId") Long panchayatId,
                                              @Param("status") SchemeStatus status,
                                              Pageable pageable);
    
    @Query("SELECT s FROM Scheme s WHERE s.panchayat.slug = :slug AND s.status IN ('ACTIVE', 'ONGOING') " +
           "ORDER BY s.createdAt DESC")
    Page<Scheme> findActiveBySlug(@Param("slug") String slug, Pageable pageable);
}

