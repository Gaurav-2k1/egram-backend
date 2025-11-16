package in.gram.gov.app.egram_service.service;

import in.gram.gov.app.egram_service.constants.exception.ResourceNotFoundException;
import in.gram.gov.app.egram_service.domain.entity.GalleryImage;
import in.gram.gov.app.egram_service.domain.repository.GalleryImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GalleryImageService {
    private final GalleryImageRepository galleryImageRepository;

    @Transactional
    public GalleryImage create(GalleryImage image) {
        return galleryImageRepository.save(image);
    }

    public GalleryImage findById(Long id) {
        return galleryImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GalleryImage", id));
    }

    public Page<GalleryImage> findByPanchayatId(Long panchayatId, Pageable pageable) {
        return galleryImageRepository.findByPanchayatId(panchayatId, pageable);
    }

    public Page<GalleryImage> findByPanchayatIdAndAlbumId(Long panchayatId, Long albumId, Pageable pageable) {
        return galleryImageRepository.findByPanchayatIdAndAlbumId(panchayatId, albumId, pageable);
    }

    public Page<GalleryImage> findByPanchayatSlugAndAlbumId(String slug, Long albumId, Pageable pageable) {
        return galleryImageRepository.findByPanchayatSlugAndAlbumId(slug, albumId, pageable);
    }

    @Transactional
    public GalleryImage update(GalleryImage image) {
        return galleryImageRepository.save(image);
    }

    @Transactional
    public void delete(Long id) {
        galleryImageRepository.deleteById(id);
    }
}

