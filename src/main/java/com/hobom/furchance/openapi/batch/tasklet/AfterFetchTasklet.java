package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobom.furchance.abandonedAnimal.entity.AbandonedAnimal;
import com.hobom.furchance.abandonedAnimal.repository.AbandonedAnimalRepository;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.HttpStatus;
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
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        try {
            List<AbandonedAnimal> abandonedAnimals = new ArrayList<>();

            for (JsonNode openApiItem : FetchOpenApiTasklet.tempStorage) {
                AbandonedAnimal abandonedAnimal = objectMapper.treeToValue(openApiItem, AbandonedAnimal.class);
                abandonedAnimals.add(abandonedAnimal);
            }

            abandonedAnimalRepository.saveAllAndFlush(abandonedAnimals);

            FetchOpenApiTasklet.tempStorage.clear();

            return RepeatStatus.FINISHED;
        } catch (Exception exception) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.JSON_ERROR + exception.getMessage());
        }
    }
}
