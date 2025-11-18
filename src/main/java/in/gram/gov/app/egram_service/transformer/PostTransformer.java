package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Post;
import in.gram.gov.app.egram_service.dto.request.PostRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PostResponseDTO;

public class PostTransformer {

    public static PostResponseDTO toDTO(Post post) {
        if (post == null) {
            return null;
        }
        
        PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBodyText(post.getBodyText());
        dto.setMediaUrl(post.getMediaUrl());
        dto.setStatus(post.getStatus());
        dto.setPublishedAt(post.getPublishedAt());
        dto.setViewCount(post.getViewCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        
        if (post.getPanchayat() != null) {
            dto.setPanchayatId(post.getPanchayat().getId());
        }
        
        if (post.getAuthor() != null) {
            dto.setAuthorId(post.getAuthor().getId());
            dto.setAuthorName(post.getAuthor().getName());
        }
        
        return dto;
    }

    public static PostResponseDTO toDTO(Post post, Long likesCount, Long commentsCount) {
        PostResponseDTO dto = toDTO(post);
        if (dto != null) {
            dto.setLikesCount(likesCount);
            dto.setCommentsCount(commentsCount);
        }
        return dto;
    }

    public static Post toEntity(PostRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Post.builder()
                .title(dto.getTitle())
                .bodyText(dto.getBodyText())
                .mediaUrl(dto.getMediaUrl())
                .build();
    }
}

