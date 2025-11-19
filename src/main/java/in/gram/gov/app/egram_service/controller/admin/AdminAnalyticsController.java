package in.gram.gov.app.egram_service.controller.admin;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.response.SystemAnalyticsResponseDTO;
import in.gram.gov.app.egram_service.facade.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminAnalyticsController {
    private final AdminFacade adminFacade;

    @GetMapping("/system")
    public ResponseEntity<ApiResponse<SystemAnalyticsResponseDTO>> getSystemAnalytics() {
        SystemAnalyticsResponseDTO response = adminFacade.getSystemAnalytics();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

