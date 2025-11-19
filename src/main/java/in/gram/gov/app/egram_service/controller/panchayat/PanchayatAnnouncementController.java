package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.AnnouncementRequestDTO;
import in.gram.gov.app.egram_service.dto.response.AnnouncementResponseDTO;
import in.gram.gov.app.egram_service.facade.AnnouncementFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/announcements")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatAnnouncementController {
    private final AnnouncementFacade announcementFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<AnnouncementResponseDTO>> create(
            @Valid @RequestBody AnnouncementRequestDTO request,
            Authentication authentication) {
        String email = authentication.getName();
        AnnouncementResponseDTO response = announcementFacade.create(request, email);
        return ResponseEntity.ok(ApiResponse.success("Announcement created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<AnnouncementResponseDTO>>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<AnnouncementResponseDTO> announcements = announcementFacade.getAll(page, size);
        PagedResponse<AnnouncementResponseDTO> response = PagedResponse.of(announcements);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AnnouncementResponseDTO>> getById(@PathVariable Long id) {
        AnnouncementResponseDTO response = announcementFacade.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AnnouncementResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequestDTO request) {
        AnnouncementResponseDTO response = announcementFacade.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Announcement updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Object>> updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean isActive) {
        announcementFacade.updateStatus(id, isActive);
        return ResponseEntity.ok(ApiResponse.success("Announcement status updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        announcementFacade.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Announcement deleted successfully", null));
    }
}

