package com.hobom.furchance.abandonedAnimal.repository;

import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbandonedAnimalRepository extends JpaRepository<AbandonedAnimal, Long> {
}
