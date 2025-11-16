package in.gram.gov.app.egram_service.controller.open;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.request.CommentRequestDTO;
import in.gram.gov.app.egram_service.dto.response.CommentResponseDTO;
import in.gram.gov.app.egram_service.facade.CommentFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/{slug}/posts/{postId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {
    private final CommentFacade commentFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CommentResponseDTO>>> getComments(
            @PathVariable String slug,
            @PathVariable Long postId,
            @PageableDefault(size = 10) Pageable pageable) {
        PagedResponse<CommentResponseDTO> response = PagedResponse.of(
                commentFacade.getPostComments(postId, pageable, true));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDTO>> addComment(
            @PathVariable String slug,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDTO request,
            HttpServletRequest httpRequest) {
        CommentResponseDTO response = commentFacade.create(postId, request, httpRequest);
        return ResponseEntity.ok(ApiResponse.success("Comment submitted for approval", response));
    }
}

