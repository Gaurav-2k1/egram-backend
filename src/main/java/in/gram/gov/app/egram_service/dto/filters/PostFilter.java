package in.gram.gov.app.egram_service.dto.filters;

import in.gram.gov.app.egram_service.constants.enums.PostStatus;
import in.gram.gov.app.egram_service.utility.PaginationParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class PostFilter extends PaginationParams {
    private PostStatus postStatus;
}
