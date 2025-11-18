package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import in.gram.gov.app.egram_service.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query("SELECT p FROM Post p WHERE p.panchayat.id = :panchayatId")
    Page<Post> findByPanchayatId(@Param("panchayatId") Long panchayatId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.panchayat.id = :panchayatId AND p.status = :status")
    Page<Post> findByPanchayatIdAndStatus(@Param("panchayatId") Long panchayatId,
                                          @Param("status") PostStatus status,
                                          Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.panchayat.slug = :slug AND p.status = 'PUBLISHED' ORDER BY p.publishedAt DESC")
    Page<Post> findPublishedBySlug(@Param("slug") String slug, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.id = :postId AND p.panchayat.slug = :slug AND p.status = 'PUBLISHED'")
    Post findPublishedByIdAndSlug(@Param("postId") Long postId, @Param("slug") String slug);
}

