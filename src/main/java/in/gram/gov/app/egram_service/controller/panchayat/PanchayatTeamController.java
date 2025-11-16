package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.StatusUpdateRequestDTO;
import in.gram.gov.app.egram_service.dto.request.UserRequestDTO;
import in.gram.gov.app.egram_service.dto.response.UserResponseDTO;
import in.gram.gov.app.egram_service.constants.enums.UserStatus;
import in.gram.gov.app.egram_service.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/team")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatTeamController {
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> addTeamMember(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userFacade.addTeamMember(request);
        return ResponseEntity.ok(ApiResponse.success("Team member added successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<UserResponseDTO>>> getTeamMembers(
            @PageableDefault(size = 10) Pageable pageable) {
        PagedResponse<UserResponseDTO> response = PagedResponse.of(userFacade.getTeamMembers(pageable));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> removeTeamMember(@PathVariable Long userId) {
        userFacade.removeTeamMember(userId);
        return ResponseEntity.ok(ApiResponse.success("Team member removed successfully", null));
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<Object>> updateTeamMemberStatus(
            @PathVariable Long userId,
            @Valid @RequestBody StatusUpdateRequestDTO request) {
        UserStatus status = UserStatus.valueOf(request.getStatus().toUpperCase());
        userFacade.updateTeamMemberStatus(userId, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", null));
    }
}

