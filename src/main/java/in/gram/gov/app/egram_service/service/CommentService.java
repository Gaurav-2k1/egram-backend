package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.exception.ResourceNotFoundException;
import in.gram.gov.app.egram_service.domain.entity.Comment;
import in.gram.gov.app.egram_service.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
    }

    public Page<Comment> findTopLevelByPostId(Long postId, Pageable pageable) {
        return commentRepository.findTopLevelByPostId(postId, pageable);
    }

    public Page<Comment> findApprovedByPostId(Long postId, Pageable pageable) {
        return commentRepository.findApprovedByPostId(postId, pageable);
    }

    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public List<Comment> findRepliesByParentId(Long parentId) {
        return commentRepository.findRepliesByParentId(parentId);
    }

    @Transactional
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public void approve(Long id) {
        Comment comment = findById(id);
        comment.setApprovedFlag(true);
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}

