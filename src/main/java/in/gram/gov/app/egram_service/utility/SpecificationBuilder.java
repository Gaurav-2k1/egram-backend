package in.gram.gov.app.egram_service.utility;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

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
