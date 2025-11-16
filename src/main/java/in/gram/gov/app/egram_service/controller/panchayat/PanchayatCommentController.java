package in.gram.gov.app.egram_service.controller.panchayat;

import in.gram.gov.app.egram_service.dto.ApiResponse;
import in.gram.gov.app.egram_service.dto.PagedResponse;
import in.gram.gov.app.egram_service.dto.response.CommentResponseDTO;
import in.gram.gov.app.egram_service.facade.CommentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panchayat/posts/{postId}/comments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PANCHAYAT_ADMIN')")
public class PanchayatCommentController {
    private final CommentFacade commentFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CommentResponseDTO>>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "false") boolean approvedOnly,
            @PageableDefault(size = 10) Pageable pageable) {
        PagedResponse<CommentResponseDTO> response = PagedResponse.of(
                commentFacade.getPostComments(postId, pageable, approvedOnly));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{commentId}/approve")
    public ResponseEntity<ApiResponse<Object>> approveComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        commentFacade.approve(postId, commentId);
        return ResponseEntity.ok(ApiResponse.success("Comment approved successfully", null));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        commentFacade.delete(postId, commentId);
        return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully", null));
    }
}

