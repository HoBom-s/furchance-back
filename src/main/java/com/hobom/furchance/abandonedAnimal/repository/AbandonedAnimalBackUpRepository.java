package com.hobom.furchance.abandonedAnimal.repository;

import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimalBackUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbandonedAnimalBackUpRepository extends JpaRepository<AbandonedAnimalBackUp, Long> {
}
