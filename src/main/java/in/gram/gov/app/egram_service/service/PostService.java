package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import in.gram.gov.app.egram_service.constants.exception.ResourceNotFoundException;
import in.gram.gov.app.egram_service.domain.entity.Post;
import in.gram.gov.app.egram_service.domain.repository.PostRepository;
import in.gram.gov.app.egram_service.dto.filters.PostFilter;
import in.gram.gov.app.egram_service.utility.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    public Page<Post> findByPanchayatId(Long panchayatId, Pageable pageable) {
        return postRepository.findByPanchayatId(panchayatId, pageable);
    }

    public Page<Post> findByPanchayatIdAndStatus(Long panchayatId, PostStatus status, Pageable pageable) {
        return postRepository.findByPanchayatIdAndStatus(panchayatId, status, pageable);
    }

    public Page<Post> findPublishedBySlug(String slug, Pageable pageable) {
        return postRepository.findPublishedBySlug(slug, pageable);
    }

    public Post findPublishedByIdAndSlug(Long postId, String slug) {
        Post post = postRepository.findPublishedByIdAndSlug(postId, slug);
        if (post == null) {
            throw new ResourceNotFoundException("Post", postId);
        }
        return post;
    }

    @Transactional
    public Post update(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void publish(Long id) {
        Post post = findById(id);
        post.setStatus(PostStatus.PUBLISHED);
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void incrementViewCount(Long id) {
        Post post = findById(id);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    public Specification<Post> buildSpecification(PostFilter filter) {
        return SpecificationBuilder.<Post>builder()
                .equalTo("status", filter.getPostStatus())
                .build();
    }

    public Page<Post> findAllByFilter(PostFilter postFilter) {
        Pageable pageable = postFilter.createPageable(postFilter);
        Specification<Post> postSpecification = buildSpecification(postFilter);
        return postRepository.findAll(postSpecification, pageable);
    }
}

