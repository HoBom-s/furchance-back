package com.hobom.furchance.domain.openapi.batch;

import com.hobom.furchance.domain.openapi.batch.tasklet.AfterFetchTasklet;
import com.hobom.furchance.domain.openapi.batch.tasklet.BeforeFetchTasklet;
import com.hobom.furchance.domain.openapi.batch.tasklet.FetchOpenApiTasklet;
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
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class OpenApiBatchConfiguration {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job openApiJob(
            @Qualifier("beforeFetch") Step beforeFetch,
            @Qualifier("fetchOpenApi") Step fetchOpenApi,
            @Qualifier("afterFetch") Step afterFetch
    ) {

        return new JobBuilder("openApiJob", jobRepository)
                .start(beforeFetch)
                .next(fetchOpenApi)
                .next(afterFetch)
                .build();
    }

    @Bean
    public Step beforeFetch(BeforeFetchTasklet beforeFetchTasklet) {

        return new StepBuilder("beforeFetch", jobRepository)
                .tasklet(beforeFetchTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step fetchOpenApi(FetchOpenApiTasklet fetchOpenApiTasklet) {

        return new StepBuilder("fetchOpenApi", jobRepository)
                .tasklet(fetchOpenApiTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step afterFetch(AfterFetchTasklet afterFetchTasklet) {

        return new StepBuilder("afterFetch", jobRepository)
                .tasklet(afterFetchTasklet, transactionManager)
                .build();
    }
}
