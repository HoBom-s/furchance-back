package com.hobom.furchance.openapi.batch.tasklet;

import com.fasterxml.jackson.databind.JsonNode;
import com.hobom.furchance.constant.OpenApiConstant;
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
public class OpenApiTasklet implements Tasklet {

    private final OpenApiClient openApiClient;

    public static List<JsonNode> tempDB = new ArrayList<>();

    @Value("${openapi.authKey.d}")
    public String OPENAPI_AUTHKEY_D;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String abandonedAnimalsInJsonString = openApiClient.getAbandonedAnimals(
                OPENAPI_AUTHKEY_D
                , OpenApiConstant.SEARCH_BEGIN_DATE
                , OpenApiConstant.SEARCH_END_DATE
                , OpenApiConstant.SEOUL_CODE
                , OpenApiConstant.COUNT
                , OpenApiConstant.TYPE_JSON
        );

        JsonNode abandonedAnimals = parseStringToJson(abandonedAnimalsInJsonString).path("response").path("body").path("items").path("item");
        System.out.println("JSON response = " + abandonedAnimals);

        for (JsonNode abandonedAnimal : abandonedAnimals) {
            System.out.println("abandonedAnimal = " + abandonedAnimal);
        }

        return RepeatStatus.FINISHED;
    }

}
