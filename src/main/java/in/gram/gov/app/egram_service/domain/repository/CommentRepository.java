package in.gram.gov.app.egram_service.domain.repository;

import in.gram.gov.app.egram_service.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.parentComment IS NULL ORDER BY c.createdAt DESC")
    Page<Comment> findTopLevelByPostId(@Param("postId") Long postId, Pageable pageable);
    
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.approvedFlag = true ORDER BY c.createdAt DESC")
    Page<Comment> findApprovedByPostId(@Param("postId") Long postId, Pageable pageable);
    
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);
    
    @Query("SELECT c FROM Comment c WHERE c.parentComment.commentId = :parentId ORDER BY c.createdAt ASC")
    List<Comment> findRepliesByParentId(@Param("parentId") Long parentId);
}

