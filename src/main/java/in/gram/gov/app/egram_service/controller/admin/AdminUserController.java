package in.gram.gov.app.egram_service.controller.admin;

import in.gram.gov.app.egram_service.constants.enums.UserRole;
import in.gram.gov.app.egram_service.constants.enums.UserStatus;
import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.StatusUpdateRequestDTO;
import in.gram.gov.app.egram_service.dto.response.UserResponseDTO;
import in.gram.gov.app.egram_service.facade.AdminFacade;
import in.gram.gov.app.egram_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminUserController {
    private final AdminFacade adminFacade;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<UserResponseDTO>>> getAllUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) UserStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<UserResponseDTO> response = PagedResponse.of(
                adminFacade.getAllUsers(role, status, pageable));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Object>> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequestDTO request) {
        UserStatus status = UserStatus.valueOf(request.getStatus().toUpperCase());
        userService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("User status updated successfully", null));
    }
}

