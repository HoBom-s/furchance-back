package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.databind.JsonNode;
import com.hobom.furchance.openapi.dto.OpenApiRequestParamDto;
import com.hobom.furchance.openapi.OpenApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.hobom.furchance.util.Util.CustomParser.parseStringToJson;

@Component
@StepScope
@RequiredArgsConstructor
public class FetchOpenApiTasklet implements Tasklet {

    private final OpenApiClient openApiClient;

    public static List<JsonNode> tempStorage = new ArrayList<>();

    @Value("${openapi.authKey-d}")
    private String serviceKey;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        OpenApiRequestParamDto openApiRequestParamDto = new OpenApiRequestParamDto();
        openApiRequestParamDto.setServiceKey(serviceKey);

        String responseInString = openApiClient.getOpenApiAbandonedAnimals(openApiRequestParamDto);

        JsonNode abandonedAnimals = parseStringToJson(responseInString).path("response").path("body").path("items").path("item");

        abandonedAnimals.forEach(tempStorage::add);

        return RepeatStatus.FINISHED;
    }
}
