package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalBackUpRepository;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class DatabaseTasklet implements Tasklet {

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    private final AbandonedAnimalBackUpRepository abandonedAnimalBackUpRepository;

    private final ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        abandonedAnimalBackUpRepository.deleteAll();

        entityManager.createNativeQuery("INSERT INTO abandoned_animal_bak SELECT * FROM abandoned_animal").executeUpdate();

        abandonedAnimalRepository.deleteAll();

        for (JsonNode openApiAbandonedAnimal : OpenApiTasklet.tempStorage) {

            AbandonedAnimal abandonedAnimal = objectMapper.treeToValue(openApiAbandonedAnimal, AbandonedAnimal.class);

            abandonedAnimalRepository.save(abandonedAnimal);
        }

        return RepeatStatus.FINISHED;
    }
}
