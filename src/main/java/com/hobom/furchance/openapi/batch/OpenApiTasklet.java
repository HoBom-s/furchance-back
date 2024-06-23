package com.hobom.furchance.openapi.batch;

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

import java.util.Map;

import static com.hobom.furchance.util.Util.CustomParser.parseStringToMap;

@Component
@StepScope
@RequiredArgsConstructor
public class OpenApiTasklet implements Tasklet {

    private final OpenApiClient openApiClient;

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

        Map<String, String> response = parseStringToMap(abandonedAnimalsInJsonString);

        System.out.println("Map response = " + response);

        return RepeatStatus.FINISHED;
    }

}
