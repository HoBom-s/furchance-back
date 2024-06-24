package com.hobom.furchance.openapi.batch;

import com.hobom.furchance.openapi.batch.tasklet.OpenApiTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class OpenApiBatchConfiguration {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job openApiJob(Step step) {
        return new JobBuilder("openApiJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step openApiStep(OpenApiTasklet tasklet) {
        return new StepBuilder("openApiStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }


}
