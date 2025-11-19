package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Album;
import in.gram.gov.app.egram_service.domain.entity.GalleryImage;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.domain.entity.User;
import in.gram.gov.app.egram_service.dto.request.GalleryImageRequestDTO;
import in.gram.gov.app.egram_service.dto.response.GalleryImageResponseDTO;
import in.gram.gov.app.egram_service.service.AlbumService;
import in.gram.gov.app.egram_service.service.GalleryImageService;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.service.UserService;
import in.gram.gov.app.egram_service.transformer.GalleryImageTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GalleryImageFacade {
    private final GalleryImageService galleryImageService;
    private final PanchayatService panchayatService;
    private final AlbumService albumService;
    private final UserService userService;

    @Transactional
    public GalleryImageResponseDTO create(GalleryImageRequestDTO request, String email) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);
        User uploader = userService.findByEmail(email);

        GalleryImage image = GalleryImageTransformer.toEntity(request);
        image.setPanchayat(panchayat);
        image.setUploadedBy(uploader);

        if (request.getAlbumId() != null) {
            Album album = albumService.findById(request.getAlbumId());
            image.setAlbum(album);
        }

        image = galleryImageService.create(image);
        return GalleryImageTransformer.toDTO(image);
    }

    public GalleryImageResponseDTO getById(Long id) {
        GalleryImage image = galleryImageService.findById(id);
        return GalleryImageTransformer.toDTO(image);
    }

    public Page<GalleryImageResponseDTO> getAll(Integer page, Integer size, Long albumId) {
        Long tenantId = TenantContext.getTenantId();
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.ASC, "displayOrder")
                    .and(Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        
        Page<GalleryImage> images;
        if (albumId != null) {
            images = galleryImageService.findByPanchayatIdAndAlbumId(tenantId, albumId, pageable);
        } else {
            images = galleryImageService.findByPanchayatId(tenantId, pageable);
        }
        
        return images.map(GalleryImageTransformer::toDTO);
    }

    @Transactional
    public GalleryImageResponseDTO update(Long id, GalleryImageRequestDTO request) {
        GalleryImage image = galleryImageService.findById(id);
        
        if (request.getImageUrl() != null) {
            image.setImageUrl(request.getImageUrl());
        }
        if (request.getCaption() != null) {
            image.setCaption(request.getCaption());
        }
        if (request.getTags() != null) {
            image.setTags(request.getTags());
        }
        if (request.getDisplayOrder() != null) {
            image.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getAlbumId() != null) {
            Album album = albumService.findById(request.getAlbumId());
            image.setAlbum(album);
        } else if (request.getAlbumId() == null && image.getAlbum() != null) {
            // Allow removing from album by passing null
            image.setAlbum(null);
        }
        
        image = galleryImageService.update(image);
        return GalleryImageTransformer.toDTO(image);
    }

    @Transactional
    public void delete(Long id) {
        galleryImageService.delete(id);
    }

    public Page<GalleryImageResponseDTO> getBySlug(String slug, Integer page, Integer size, Long albumId) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.ASC, "displayOrder")
                    .and(Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        
        Page<GalleryImage> images;
        if (albumId != null) {
            images = galleryImageService.findByPanchayatSlugAndAlbumId(slug, albumId, pageable);
        } else {
            // Need to add a method to find by slug without album
            // For now, we'll get panchayat by slug first
            Panchayat panchayat = panchayatService.findBySlug(slug);
            images = galleryImageService.findByPanchayatId(panchayat.getId(), pageable);
        }
        
        return images.map(GalleryImageTransformer::toDTO);
    }
}

