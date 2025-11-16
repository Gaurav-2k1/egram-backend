package in.gram.gov.app.egram_service.controller.admin;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.filters.PanchayatFilter;
import in.gram.gov.app.egram_service.dto.request.PanchayatRequestDTO;
import in.gram.gov.app.egram_service.dto.request.StatusUpdateRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatStatsResponseDTO;
import in.gram.gov.app.egram_service.constants.enums.PanchayatStatus;
import in.gram.gov.app.egram_service.facade.PanchayatFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/panchayats")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminPanchayatController {
    private final PanchayatFacade panchayatFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> create(@Valid @RequestBody PanchayatRequestDTO request) {
        PanchayatResponseDTO response = panchayatFacade.create(request);
        return ResponseEntity.ok(ApiResponse.success("Panchayat created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<PanchayatResponseDTO>>> getAll(
            PanchayatFilter panchayatFilter) {
        PagedResponse<PanchayatResponseDTO> response = PagedResponse.of(
                panchayatFacade.getAll(panchayatFilter));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> getById(@PathVariable Long id) {
        PanchayatResponseDTO response = panchayatFacade.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody PanchayatRequestDTO request) {
        PanchayatResponseDTO response = panchayatFacade.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Panchayat updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Object>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequestDTO request) {
        PanchayatStatus status = PanchayatStatus.valueOf(request.getStatus().toUpperCase());
        panchayatFacade.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        panchayatFacade.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Panchayat deleted successfully", null));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse<PanchayatStatsResponseDTO>> getStats(@PathVariable Long id) {
        PanchayatStatsResponseDTO response = panchayatFacade.getStats(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

