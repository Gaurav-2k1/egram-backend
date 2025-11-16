package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.domain.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.panchayat.panchayatId = :panchayatId")
    Page<Announcement> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);
    
    @Query("SELECT a FROM Announcement a WHERE a.panchayat.slug = :slug AND a.isActive = true AND " +
           "(a.startDate IS NULL OR a.startDate <= :today) AND " +
           "(a.endDate IS NULL OR a.endDate >= :today) " +
           "ORDER BY a.priority DESC, a.createdAt DESC")
    Page<Announcement> findActiveBySlug(@Param("slug") String slug, 
                                         @Param("today") LocalDate today, 
                                         Pageable pageable);
}

