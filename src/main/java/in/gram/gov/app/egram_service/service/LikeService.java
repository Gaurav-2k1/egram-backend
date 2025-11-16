package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.domain.entity.Like;
import in.gram.gov.app.egram_service.domain.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public Like create(Like like) {
        return likeRepository.save(like);
    }

    public Optional<Like> findByPostIdAndUserId(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId);
    }

    public Optional<Like> findByPostIdAndVisitorIdentifier(Long postId, String visitorIdentifier) {
        return likeRepository.findByPostIdAndVisitorIdentifier(postId, visitorIdentifier);
    }

    public Long countByPostId(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Transactional
    public void delete(Long id) {
        likeRepository.deleteById(id);
    }
}

