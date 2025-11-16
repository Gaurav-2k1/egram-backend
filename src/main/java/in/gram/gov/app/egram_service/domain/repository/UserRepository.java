package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.constants.enums.UserRole;
import in.gram.gov.app.egram_service.constants.enums.UserStatus;
import in.gram.gov.app.egram_service.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.panchayat.panchayatId = :panchayatId AND u.role = :role")
    List<User> findByPanchayatIdAndRole(@Param("panchayatId") Long panchayatId, @Param("role") UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.panchayat.panchayatId = :panchayatId AND u.role = :role AND u.status = :status")
    Long countByPanchayatIdAndRoleAndStatus(@Param("panchayatId") Long panchayatId, 
                                             @Param("role") UserRole role,
                                             @Param("status") UserStatus status);
    
    @Query("SELECT u FROM User u WHERE u.panchayat.panchayatId = :panchayatId")
    Page<User> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:panchayatId IS NULL OR u.panchayat.panchayatId = :panchayatId) AND " +
           "(:role IS NULL OR u.role = :role) AND " +
           "(:status IS NULL OR u.status = :status)")
    Page<User> findByFilters(@Param("panchayatId") Long panchayatId,
                             @Param("role") UserRole role,
                             @Param("status") UserStatus status,
                             Pageable pageable);
    
    Optional<User> findByPasswordResetToken(String token);
}

