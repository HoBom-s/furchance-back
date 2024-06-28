package com.hobom.furchance.openapi.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class OpenApiQuartzJobLauncher extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobLauncher jobLauncher = (JobLauncher) context.getMergedJobDataMap().get("jobLauncher");
        Job job = (Job) context.getMergedJobDataMap().get("job");

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runDate", new Date())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            if (!jobExecution.getExitStatus().getExitCode().equals("COMPLETED")) {
                throw new JobExecutionException("Job failed with status: " + jobExecution.getExitStatus());
            }

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
