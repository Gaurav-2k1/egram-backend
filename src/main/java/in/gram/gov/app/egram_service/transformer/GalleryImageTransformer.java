package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.GalleryImage;
import in.gram.gov.app.egram_service.dto.request.GalleryImageRequestDTO;
import in.gram.gov.app.egram_service.dto.response.GalleryImageResponseDTO;

public class GalleryImageTransformer {

    public static GalleryImageResponseDTO toDTO(GalleryImage image) {
        if (image == null) {
            return null;
        }
        
        GalleryImageResponseDTO dto = new GalleryImageResponseDTO();
        dto.setImageId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setCaption(image.getCaption());
        dto.setTags(image.getTags());
        dto.setDisplayOrder(image.getDisplayOrder());
        dto.setCreatedAt(image.getCreatedAt());
        dto.setUpdatedAt(image.getUpdatedAt());
        
        if (image.getAlbum() != null) {
            dto.setAlbumId(image.getAlbum().getId());
            dto.setAlbumName(image.getAlbum().getAlbumName());
        }
        
        return dto;
    }

    public static GalleryImage toEntity(GalleryImageRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return GalleryImage.builder()
                .imageUrl(dto.getImageUrl())
                .caption(dto.getCaption())
                .tags(dto.getTags())
                .displayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0)
                .build();
    }
}

