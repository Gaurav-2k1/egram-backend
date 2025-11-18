package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.domain.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.post.id = :postId AND l.user.id = :userId")
    Optional<Like> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
    
    @Query("SELECT l FROM Like l WHERE l.post.id = :postId AND l.visitorIdentifier = :visitorIdentifier")
    Optional<Like> findByPostIdAndVisitorIdentifier(@Param("postId") Long postId,
                                                      @Param("visitorIdentifier") String visitorIdentifier);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId AND l.user IS NOT NULL")
    Long countAuthenticatedLikesByPostId(@Param("postId") Long postId);
}

