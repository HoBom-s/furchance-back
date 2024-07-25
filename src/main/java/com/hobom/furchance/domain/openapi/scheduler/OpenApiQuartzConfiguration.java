package com.hobom.furchance.domain.openapi.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiQuartzConfiguration {

    private final Job openApiJob;

    private final JobLauncher jobLauncher;

    @Bean
    public JobDetail jobDetail() {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("jobName", openApiJob.getName());
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("job", openApiJob);

        return JobBuilder.newJob(OpenApiQuartzJobLauncher.class)
                .withIdentity("openApiJobDetail")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("openApiJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }
}
