package in.gram.gov.app.egram_service.transformer;

import in.gram.gov.app.egram_service.domain.entity.Album;
import in.gram.gov.app.egram_service.dto.request.AlbumRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AlbumResponseDTO;

public class AlbumTransformer {

    public static AlbumResponseDTO toDTO(Album album) {
        if (album == null) {
            return null;
        }
        
        AlbumResponseDTO dto = new AlbumResponseDTO();
        dto.setAlbumId(album.getId());
        dto.setAlbumName(album.getAlbumName());
        dto.setDescription(album.getDescription());
        dto.setCoverImageUrl(album.getCoverImageUrl());
        dto.setImageCount(album.getImages() != null ? (long) album.getImages().size() : 0L);
        dto.setCreatedAt(album.getCreatedAt());
        dto.setUpdatedAt(album.getUpdatedAt());
        
        return dto;
    }

    public static Album toEntity(AlbumRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Album.builder()
                .albumName(dto.getAlbumName())
                .description(dto.getDescription())
                .coverImageUrl(dto.getCoverImageUrl())
                .build();
    }
}

