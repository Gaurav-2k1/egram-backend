package in.gram.gov.app.egram_service.facade;

import in.gram.gov.app.egram_service.constants.security.TenantContext;
import in.gram.gov.app.egram_service.domain.entity.Album;
import in.gram.gov.app.egram_service.domain.entity.Panchayat;
import in.gram.gov.app.egram_service.dto.request.AlbumRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AlbumResponseDTO;
import in.gram.gov.app.egram_service.service.AlbumService;
import in.gram.gov.app.egram_service.service.PanchayatService;
import in.gram.gov.app.egram_service.transformer.AlbumTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumFacade {
    private final AlbumService albumService;
    private final PanchayatService panchayatService;

    @Transactional
    public AlbumResponseDTO create(AlbumRequestDTO request) {
        Long tenantId = TenantContext.getTenantId();
        Panchayat panchayat = panchayatService.findById(tenantId);

        Album album = AlbumTransformer.toEntity(request);
        album.setPanchayat(panchayat);

        album = albumService.create(album);
        return AlbumTransformer.toDTO(album);
    }

    public AlbumResponseDTO getById(Long id) {
        Album album = albumService.findById(id);
        return AlbumTransformer.toDTO(album);
    }

    public Page<AlbumResponseDTO> getAll(Integer page, Integer size) {
        Long tenantId = TenantContext.getTenantId();
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        Page<Album> albums = albumService.findByPanchayatId(tenantId, pageable);
        return albums.map(AlbumTransformer::toDTO);
    }

    @Transactional
    public AlbumResponseDTO update(Long id, AlbumRequestDTO request) {
        Album album = albumService.findById(id);
        
        if (request.getAlbumName() != null) {
            album.setAlbumName(request.getAlbumName());
        }
        if (request.getDescription() != null) {
            album.setDescription(request.getDescription());
        }
        if (request.getCoverImageUrl() != null) {
            album.setCoverImageUrl(request.getCoverImageUrl());
        }
        
        album = albumService.update(album);
        return AlbumTransformer.toDTO(album);
    }

    @Transactional
    public void delete(Long id) {
        albumService.delete(id);
    }
}

