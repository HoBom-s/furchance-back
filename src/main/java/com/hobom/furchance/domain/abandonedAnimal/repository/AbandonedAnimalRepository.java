package com.hobom.furchance.domain.abandonedAnimal.repository;

import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AbandonedAnimalRepository extends JpaRepository<AbandonedAnimal, Long> , JpaSpecificationExecutor<AbandonedAnimal> {
}
