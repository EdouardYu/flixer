package web.technologies.flixer.repository.specification;


import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import web.technologies.flixer.dto.SearchCriteria;
import web.technologies.flixer.entity.Movie;

import java.util.Collection;

@AllArgsConstructor
public class MovieSpecification implements Specification<Movie> {
    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate
        (@Nonnull Root<Movie> root, @Nonnull CriteriaQuery<?> query, @Nonnull CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class)
                return builder.like(
                    root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            else
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());


        } else if (criteria.getOperation().equalsIgnoreCase("!:")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class)
                return builder.not(
                    builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
            else
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getOperation().equalsIgnoreCase("in")) {
            if (criteria.getValue() instanceof Collection)
                return root.get(criteria.getKey()).in((Collection<?>) criteria.getValue());

        } else if (criteria.getOperation().equalsIgnoreCase("!in")) {
            if (criteria.getValue() instanceof Collection)
                return builder.not(root.get(criteria.getKey()).in((Collection<?>) criteria.getValue()));
        }

        return null;
    }
}
