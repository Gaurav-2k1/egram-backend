package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.GalleryImageRequestDTO;
import in.gram.gov.app.egram_service.dto.response.GalleryImageResponseDTO;
import in.gram.gov.app.egram_service.facade.GalleryImageFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/gallery")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatGalleryController {
    private final GalleryImageFacade galleryImageFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<GalleryImageResponseDTO>> create(
            @Valid @RequestBody GalleryImageRequestDTO request,
            Authentication authentication) {
        String email = authentication.getName();
        GalleryImageResponseDTO response = galleryImageFacade.create(request, email);
        return ResponseEntity.ok(ApiResponse.success("Image uploaded successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<GalleryImageResponseDTO>>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) Long albumId) {
        Page<GalleryImageResponseDTO> images = galleryImageFacade.getAll(page, size, albumId);
        PagedResponse<GalleryImageResponseDTO> response = PagedResponse.of(images);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GalleryImageResponseDTO>> getById(@PathVariable Long id) {
        GalleryImageResponseDTO response = galleryImageFacade.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GalleryImageResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody GalleryImageRequestDTO request) {
        GalleryImageResponseDTO response = galleryImageFacade.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Image updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        galleryImageFacade.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully", null));
    }
}

