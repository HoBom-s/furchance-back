package com.hobom.furchance.domain.abandonedAnimal.repository;

import com.hobom.furchance.domain.abandonedAnimal.entity.AbandonedAnimalBackUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbandonedAnimalBackUpRepository extends JpaRepository<AbandonedAnimalBackUp, Long> {
}
