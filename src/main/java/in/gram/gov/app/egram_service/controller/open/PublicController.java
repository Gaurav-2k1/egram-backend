package in.gram.gov.app.egram_service.controller.open;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.filters.PanchayatFilter;
import in.gram.gov.app.egram_service.dto.response.AnnouncementResponseDTO;
import in.gram.gov.app.egram_service.dto.response.GalleryImageResponseDTO;
import in.gram.gov.app.egram_service.dto.response.PanchayatResponseDTO;
import in.gram.gov.app.egram_service.dto.response.PostResponseDTO;
import in.gram.gov.app.egram_service.dto.response.SchemeResponseDTO;
import in.gram.gov.app.egram_service.dto.response.UserResponseDTO;
import in.gram.gov.app.egram_service.facade.AnnouncementFacade;
import in.gram.gov.app.egram_service.facade.GalleryImageFacade;
import in.gram.gov.app.egram_service.facade.PanchayatFacade;
import in.gram.gov.app.egram_service.facade.PostFacade;
import in.gram.gov.app.egram_service.facade.SchemeFacade;
import in.gram.gov.app.egram_service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final PanchayatFacade panchayatFacade;
    private final PostFacade postFacade;
    private final SchemeFacade schemeFacade;
    private final AnnouncementFacade announcementFacade;
    private final GalleryImageFacade galleryImageFacade;
    private final UserFacade userFacade;

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

    @GetMapping("/{slug}/schemes")
    public ResponseEntity<ApiResponse<PagedResponse<SchemeResponseDTO>>> getSchemes(
            @PathVariable String slug,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<SchemeResponseDTO> schemes = schemeFacade.getActiveBySlug(slug, page, size);
        PagedResponse<SchemeResponseDTO> response = PagedResponse.of(schemes);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/announcements")
    public ResponseEntity<ApiResponse<PagedResponse<AnnouncementResponseDTO>>> getAnnouncements(
            @PathVariable String slug,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<AnnouncementResponseDTO> announcements = announcementFacade.getActiveBySlug(slug, page, size);
        PagedResponse<AnnouncementResponseDTO> response = PagedResponse.of(announcements);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/gallery")
    public ResponseEntity<ApiResponse<PagedResponse<GalleryImageResponseDTO>>> getGallery(
            @PathVariable String slug,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) Long albumId) {
        Page<GalleryImageResponseDTO> images = galleryImageFacade.getBySlug(slug, page, size, albumId);
        PagedResponse<GalleryImageResponseDTO> response = PagedResponse.of(images);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{slug}/members")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponseDTO>>> getMembers(
            @PathVariable String slug,
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<UserResponseDTO> response = PagedResponse.of(userFacade.getTeamMembersBySlug(slug, pageable));
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

