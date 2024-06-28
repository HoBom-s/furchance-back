package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.AbandonedAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class AfterFetchTasklet implements Tasklet {

    private final ObjectMapper objectMapper;

    private final AbandonedAnimalRepository abandonedAnimalRepository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {


        List<AbandonedAnimal> abandonedAnimals = new ArrayList<>();

        for(JsonNode openApiItem : FetchOpenApiTasklet.tempStorage) {
            AbandonedAnimal abandonedAnimal = objectMapper.treeToValue(openApiItem, AbandonedAnimal.class);
            abandonedAnimals.add(abandonedAnimal);
        }

        abandonedAnimalRepository.saveAllAndFlush(abandonedAnimals);

        FetchOpenApiTasklet.tempStorage.clear();

        return RepeatStatus.FINISHED;
    }
}
