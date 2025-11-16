package in.gram.gov.app.egram_service.utility;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationParams {
    @Min(
            value = 0L,
            message = "Page number must be 1 or greater"
    )
    int pageNumber = 0;
    @Min(
            value = 1L,
            message = "Page size must be 1 or greater"
    )
    @Max(
            value = 9999L,
            message = "Page size must be 9999 or less"
    )
    int pageSize = 10;
    String sortBy = "panchayatId";
    Sort.Direction sortOrder = Sort.Direction.ASC;
    String searchQuery;


    public  <T extends PaginationParams> Pageable createPageable(T filter) {
        Sort.Direction direction = filter.getSortOrder();
        Sort sort = Sort.by(direction, filter.getSortBy());
        return PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);
    }
}