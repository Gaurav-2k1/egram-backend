package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.Post;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.filters.PostFilter;
import in.gram.gov.app.egram_service.dto.request.PostRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PostResponseDTO;
import in.gram.gov.app.egram_service.service.*;
import in.gram.gov.app.egram_service.transformer.PostTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostService postService;
    private final PanchayatService panchayatService;
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;

    @Transactional
    public PostResponseDTO create(PostRequestDTO request, String email) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);
        User author = userService.findByEmail(email);

        Post post = Post.builder()
                .title(request.getTitle())
                .bodyText(request.getBodyText())
                .mediaUrl(request.getMediaUrl())
                .panchayat(panchayat)
                .author(author)
                .status(PostStatus.DRAFT)
                .viewCount(0L)
                .build();

        post = postService.create(post);
        return mapToResponse(post);
    }

    public PostResponseDTO getById(Long id) {
        Post post = postService.findById(id);
        return mapToResponse(post);
    }

    public Page<PostResponseDTO> getAll(PostFilter postFilter) {
        Long tenantId = TenantContext.getTenantId();
        Page<Post> posts = postService.findAllByFilter(postFilter);
        return posts.map(this::mapToResponse);
    }

    public Page<PostResponseDTO> getPublishedBySlug(String slug, Pageable pageable) {
        Page<Post> posts = postService.findPublishedBySlug(slug, pageable);
        return posts.map(this::mapToResponse);
    }

    public PostResponseDTO getPublishedByIdAndSlug(Long postId, String slug) {
        Post post = postService.findPublishedByIdAndSlug(postId, slug);
        postService.incrementViewCount(postId);
        return mapToResponse(post);
    }

    @Transactional
    public PostResponseDTO update(Long id, PostRequestDTO request) {
        Post post = postService.findById(id);
        post.setTitle(request.getTitle());
        post.setBodyText(request.getBodyText());
        post.setMediaUrl(request.getMediaUrl());
        post = postService.update(post);
        return mapToResponse(post);
    }

    @Transactional
    public void publish(Long id) {
        postService.publish(id);
    }

    @Transactional
    public void delete(Long id) {
        postService.delete(id);
    }

    private PostResponseDTO mapToResponse(Post post) {
        Long likesCount = likeService.countByPostId(post.getId());
        Long commentsCount = (long) commentService.findAllByPostId(post.getId()).size();
        return PostTransformer.toDTO(post, likesCount, commentsCount);
    }
}

