package in.gram.gov.app.egram_service.controller.open;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.filters.PanchayatFilter;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;
import in.gram.gov.app.egram_service.dto.response.PostResponseDTO;
import in.gram.gov.app.egram_service.facade.PanchayatFacade;
import in.gram.gov.app.egram_service.facade.PostFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final PanchayatFacade panchayatFacade;
    private final PostFacade postFacade;

    @GetMapping("/panchayats")
    public ResponseEntity<ApiResponse<PagedResponse<PanchayatResponseDTO>>> getAllPanchayats(
            PanchayatFilter panchayatFilter) {
        PagedResponse<PanchayatResponseDTO> response = PagedResponse.of(
                panchayatFacade.getAll(panchayatFilter));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/panchayats/slug/{slug}")
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> getPanchayatBySlug(@PathVariable String slug) {
        PanchayatResponseDTO response = panchayatFacade.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/home")
    public ResponseEntity<ApiResponse<PanchayatResponseDTO>> getHome(@PathVariable String slug) {
        PanchayatResponseDTO response = panchayatFacade.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/posts")
    public ResponseEntity<ApiResponse<PagedResponse<PostResponseDTO>>> getPosts(
            @PathVariable String slug,
            @PageableDefault(size = 10) Pageable pageable) {
        PagedResponse<PostResponseDTO> response = PagedResponse.of(postFacade.getPublishedBySlug(slug, pageable));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPost(
            @PathVariable String slug,
            @PathVariable Long id) {
        PostResponseDTO response = postFacade.getPublishedByIdAndSlug(id, slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

