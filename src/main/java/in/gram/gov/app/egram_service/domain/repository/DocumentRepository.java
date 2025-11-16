package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.constants.enums.DocumentCategory;
import in.gram.gov.app.egram_service.domain.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.panchayat.panchayatId = :panchayatId")
    Page<Document> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);
    
    @Query("SELECT d FROM Document d WHERE d.panchayat.panchayatId = :panchayatId AND " +
           "(:category IS NULL OR d.category = :category)")
    Page<Document> findByPanchayatIdAndCategory(@Param("panchayatId") Long panchayatId,
                                                 @Param("category") DocumentCategory category,
                                                 Pageable pageable);
    
    @Query("SELECT d FROM Document d WHERE d.panchayat.slug = :slug ORDER BY d.createdAt DESC")
    Page<Document> findByPanchayatSlug(@Param("slug") String slug, Pageable pageable);
}

