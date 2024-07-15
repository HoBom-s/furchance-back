package com.hobom.furchance.abandonedAnimal.repository;

import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AbandonedAnimalRepository extends JpaRepository<AbandonedAnimal, Long> , JpaSpecificationExecutor<AbandonedAnimal> {
}
