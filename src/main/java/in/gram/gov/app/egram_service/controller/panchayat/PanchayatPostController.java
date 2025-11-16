package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.filters.PostFilter;
import in.gram.gov.app.egram_service.dto.request.PostRequestDTO;
import in.gram.gov.app.egram_service.dto.response.PostResponseDTO;
import in.gram.gov.app.egram_service.facade.PostFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/posts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatPostController {
    private final PostFacade postFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDTO>> create(
            @Valid @RequestBody PostRequestDTO request,
            Authentication authentication) {
        String email = authentication.getName();
        PostResponseDTO response = postFacade.create(request, email);
        return ResponseEntity.ok(ApiResponse.success("Post created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<PostResponseDTO>>> getAll(
            PostFilter postFilter) {
        PagedResponse<PostResponseDTO> response = PagedResponse.of(postFacade.getAll(postFilter));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getById(@PathVariable Long id) {
        PostResponseDTO response = postFacade.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postFacade.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", response));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<Object>> publish(@PathVariable Long id) {
        postFacade.publish(id);
        return ResponseEntity.ok(ApiResponse.success("Post published successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
        postFacade.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Post deleted successfully", null));
    }
}

