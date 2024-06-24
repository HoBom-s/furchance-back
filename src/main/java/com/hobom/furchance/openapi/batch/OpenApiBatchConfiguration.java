package com.hobom.furchance.openapi.batch;

import com.hobom.furchance.openapi.batch.tasklet.DatabaseTasklet;
import com.hobom.furchance.openapi.batch.tasklet.OpenApiTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class OpenApiBatchConfiguration {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job openApiJob(@Qualifier("getOpenApiData") Step getOpenApiData, @Qualifier("saveDataToDB") Step saveDataToDB) {
        return new JobBuilder("openApiJob", jobRepository)
                .start(getOpenApiData)
                .next(saveDataToDB)
                .build();
    }

    @Bean
    public Step getOpenApiData(OpenApiTasklet openApiTasklet) {
        return new StepBuilder("getOpenApiData", jobRepository)
                .tasklet(openApiTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step saveDataToDB(DatabaseTasklet databaseTasklet) {
        return new StepBuilder("saveDataToDB", jobRepository)
                .tasklet(databaseTasklet, transactionManager)
                .build();
    }


}
