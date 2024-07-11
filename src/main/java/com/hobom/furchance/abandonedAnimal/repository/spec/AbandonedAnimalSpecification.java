package com.hobom.furchance.abandonedAnimal.repository.spec;

import com.hobom.furchance.abandonedAnimal.dto.AbandonedAnimalPaginationRequestParamDto;
import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbandonedAnimalSpecification {
    public Specification<AbandonedAnimal> withFilters(AbandonedAnimalPaginationRequestParamDto abandonedAnimalPaginationRequestParamDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (abandonedAnimalPaginationRequestParamDto.getSearchStartDate() != null && !abandonedAnimalPaginationRequestParamDto.getSearchStartDate().isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("noticeSdt"), abandonedAnimalPaginationRequestParamDto.getSearchStartDate()));
            }

            if (abandonedAnimalPaginationRequestParamDto.getSearchEndDate() != null && !abandonedAnimalPaginationRequestParamDto.getSearchEndDate().isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("noticeSdt"), abandonedAnimalPaginationRequestParamDto.getSearchEndDate()));
            }

            if (abandonedAnimalPaginationRequestParamDto.getKind() != null && !abandonedAnimalPaginationRequestParamDto.getKind().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("kindCd"), "%" + abandonedAnimalPaginationRequestParamDto.getKind() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
