package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Comment;
import in.gram.gov.app.egram_service.dto.request.CommentRequestDTO;
import in.gram.gov.app.egram_service.dto.response.CommentResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CommentTransformer {

    public static CommentResponseDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setCommentId(comment.getId());
        dto.setCommenterName(comment.getCommenterName());
        dto.setCommenterEmail(comment.getCommenterEmail());
        dto.setBodyText(comment.getBodyText());
        dto.setApprovedFlag(comment.getApprovedFlag());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        
        if (comment.getPost() != null) {
            dto.setPostId(comment.getPost().getId());
        }
        
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }
        
        return dto;
    }

    public static CommentResponseDTO toDTOWithReplies(Comment comment, List<Comment> replies) {
        CommentResponseDTO dto = toDTO(comment);
        if (dto != null && replies != null) {
            dto.setReplies(replies.stream()
                    .map(CommentTransformer::toDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static Comment toEntity(CommentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Comment.builder()
                .commenterName(dto.getCommenterName())
                .commenterEmail(dto.getCommenterEmail())
                .bodyText(dto.getBodyText())
                .approvedFlag(false)
                .build();
    }
}

