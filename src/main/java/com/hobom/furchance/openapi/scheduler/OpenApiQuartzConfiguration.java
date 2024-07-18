package com.hobom.furchance.openapi.scheduler;

import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiQuartzConfiguration {

    @Autowired
    private Job openApiJob;

    @Autowired
    private JobLauncher jobLauncher;

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
