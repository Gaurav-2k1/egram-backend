package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.request.PanchayatRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;
import in.gram.gov.app.egram_service.facade.PanchayatFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/settings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatSettingsController {
    private final PanchayatFacade panchayatFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> getSettings() {
        PanchayatResponseDTO response = panchayatFacade.getCurrentPanchayat();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> updateSettings(
            @Valid @RequestBody PanchayatRequestDTO request) {
        PanchayatResponseDTO response = panchayatFacade.updateCurrent(request);
        return ResponseEntity.ok(ApiResponse.success("Settings updated successfully", response));
    }
}

