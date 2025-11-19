package in.gram.gov.app.egram_service.utility;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<Specification<T>> specifications = new ArrayList<>();

    public static <T> SpecificationBuilder<T> builder() {
        return new SpecificationBuilder<>();
    }

    // Basic equality operations
    public SpecificationBuilder<T> equalTo(String fieldName, Object value) {
        if (value != null) {
            specifications.add((root, query, cb) -> cb.equal(getPath(root, fieldName), value));
        }
        return this;
    }

    // Build final specification
    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification.unrestricted(), Specification::and);
    }

    public SpecificationBuilder<T> dateTimeRange(String fieldName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime != null && endDateTime != null) {
            validateFieldName(fieldName);
            if (startDateTime.isAfter(endDateTime)) {
                throw new IllegalArgumentException("Start date-time cannot be after end date-time");
            }

            specifications.add((root, query, cb) -> {
                try {
                    Path<LocalDateTime> dateTimePath = getPath(root, fieldName);
                    return cb.between(dateTimePath, startDateTime, endDateTime);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error applying date-time range filter on field: " + fieldName, e);
                }
            });
        }
        return this;
    }

    // Validation methods
    private void validateFieldName(String fieldName) {
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }
    }

    // Utility methods
    @SuppressWarnings("unchecked")
    private <Y> Path<Y> getPath(Root<T> root, String fieldName) {
        String[] fields = fieldName.split("\\.");
        Path<Y> path = (Path<Y>) root;
        for (String field : fields) {
            path = path.get(field);
        }
        return path;
    }
}
