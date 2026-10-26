package com.theinngu32.common.specifications;

import com.theinngu32.common.dto.SearchFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {
    private final List<SearchFilter> filters;

    public GenericSpecification(List<SearchFilter> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        for (SearchFilter filter : filters) {
            if (filter.getValue() == null || filter.getValue().isEmpty()) continue;

            Path<?> fieldPath;

            if (filter.getTerm().contains(".")) {
                String[] parts = filter.getTerm().split("\\.");
                Join<Object, Object> join = root.join(parts[0], JoinType.LEFT);
                fieldPath = join.get(parts[1]);
            } else {
                fieldPath = root.get(filter.getTerm());
            }

            switch (filter.getMatchType()) {
                case "contains":
                    predicate = cb.and(predicate, cb.like(cb.lower(fieldPath.as(String.class)), "%" + filter.getValue().toLowerCase() + "%"));
                    break;
                case "exact":
                    predicate = cb.and(predicate, cb.equal(fieldPath, filter.getValue()));
                    break;
                case "less_than_equal":
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(fieldPath.as(Integer.class), Integer.parseInt(filter.getValue())));
                    break;
                case "greater_than_equal":
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(fieldPath.as(Integer.class), Integer.parseInt(filter.getValue())));
                    break;
            }
        }

        return predicate;
    }
}

