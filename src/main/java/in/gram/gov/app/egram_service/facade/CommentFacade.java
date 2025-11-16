package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.dto.request.CommentRequestDTO;
import in.gram.gov.app.egram_service.dto.response.CommentResponseDTO;
import in.gram.gov.app.egram_service.domain.entity.Comment;
import in.gram.gov.app.egram_service.domain.entity.Post;
import in.gram.gov.app.egram_service.service.CommentService;
import in.gram.gov.app.egram_service.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentFacade {
    private final CommentService commentService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Transactional
    public CommentResponseDTO create(Long postId, CommentRequestDTO request, HttpServletRequest httpRequest) {
        Post post = postService.findById(postId);
        
        Comment comment = Comment.builder()
                .post(post)
                .commenterName(request.getCommenterName())
                .commenterEmail(request.getCommenterEmail())
                .bodyText(request.getBodyText())
                .approvedFlag(false)
                .ipAddress(getClientIpAddress(httpRequest))
                .build();

        if (request.getParentCommentId() != null) {
            Comment parent = commentService.findById(request.getParentCommentId());
            comment.setParentComment(parent);
        }

        comment = commentService.create(comment);
        return mapToResponse(comment);
    }

    public Page<CommentResponseDTO> getPostComments(Long postId, Pageable pageable, boolean approvedOnly) {
        Page<Comment> comments = approvedOnly
                ? commentService.findApprovedByPostId(postId, pageable)
                : commentService.findTopLevelByPostId(postId, pageable);
        
        return comments.map(this::mapToResponseWithReplies);
    }

    @Transactional
    public void approve(Long postId, Long commentId) {
        Comment comment = commentService.findById(commentId);
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to this post");
        }
        commentService.approve(commentId);
    }

    @Transactional
    public void delete(Long postId, Long commentId) {
        Comment comment = commentService.findById(commentId);
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to this post");
        }
        commentService.delete(commentId);
    }

    private CommentResponseDTO mapToResponse(Comment comment) {
        CommentResponseDTO dto = modelMapper.map(comment, CommentResponseDTO.class);
        dto.setPostId(comment.getPost().getPostId());
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getCommentId());
        }
        return dto;
    }

    private CommentResponseDTO mapToResponseWithReplies(Comment comment) {
        CommentResponseDTO dto = mapToResponse(comment);
        List<Comment> replies = commentService.findRepliesByParentId(comment.getCommentId());
        dto.setReplies(replies.stream().map(this::mapToResponse).collect(Collectors.toList()));
        return dto;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

