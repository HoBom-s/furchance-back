package com.hobom.furchance.abandonedAnimal.repository.spec;

import com.hobom.furchance.abandonedAnimal.dto.PaginationRequestParamDto;
import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbandonedAnimalSpecification {
    public Specification<AbandonedAnimal> withFilters(PaginationRequestParamDto paginationRequestParamDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (paginationRequestParamDto.getSearchStartDate() != null && !paginationRequestParamDto.getSearchStartDate().isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("noticeSdt"), paginationRequestParamDto.getSearchStartDate()));
            }

            if (paginationRequestParamDto.getSearchEndDate() != null && !paginationRequestParamDto.getSearchEndDate().isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("noticeSdt"), paginationRequestParamDto.getSearchEndDate()));
            }

            if (paginationRequestParamDto.getKind() != null && !paginationRequestParamDto.getKind().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("kindCd"), "%" + paginationRequestParamDto.getKind() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
