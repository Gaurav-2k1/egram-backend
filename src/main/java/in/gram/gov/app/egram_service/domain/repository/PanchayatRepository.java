package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.constants.enums.PanchayatStatus;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PanchayatRepository extends JpaRepository<Panchayat, Long>, JpaSpecificationExecutor<Panchayat> {
    Optional<Panchayat> findBySlug(String slug);
    boolean existsBySlug(String slug);
    Page<Panchayat> findByStatus(PanchayatStatus status, Pageable pageable);
    
    @Query("SELECT p FROM Panchayat p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:district IS NULL OR LOWER(p.district) LIKE LOWER(CONCAT('%', :district, '%'))) AND " +
           "(:state IS NULL OR LOWER(p.state) LIKE LOWER(CONCAT('%', :state, '%')))")
    Page<Panchayat> findByFilters(@Param("status") PanchayatStatus status,
                                   @Param("district") String district,
                                   @Param("state") String state,
                                   Pageable pageable);
}

