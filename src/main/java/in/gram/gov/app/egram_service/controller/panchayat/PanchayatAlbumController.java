package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.AlbumRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AlbumResponseDTO;
import in.gram.gov.app.egram_service.facade.AlbumFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/albums")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatAlbumController {
    private final AlbumFacade albumFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<AlbumResponseDTO>> create(
            @Valid @RequestBody AlbumRequestDTO request) {
        AlbumResponseDTO response = albumFacade.create(request);
        return ResponseEntity.ok(ApiResponse.success("Album created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<AlbumResponseDTO>>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<AlbumResponseDTO> albums = albumFacade.getAll(page, size);
        PagedResponse<AlbumResponseDTO> response = PagedResponse.of(albums);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlbumResponseDTO>> getById(@PathVariable Long id) {
        AlbumResponseDTO response = albumFacade.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AlbumResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO request) {
        AlbumResponseDTO response = albumFacade.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Album updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        albumFacade.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Album deleted successfully", null));
    }
}

