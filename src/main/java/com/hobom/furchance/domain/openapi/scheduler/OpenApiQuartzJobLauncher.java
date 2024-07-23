package com.hobom.furchance.domain.openapi.scheduler;

import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class OpenApiQuartzJobLauncher extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobLauncher jobLauncher = (JobLauncher) context.getMergedJobDataMap().get("jobLauncher");
        Job job = (Job) context.getMergedJobDataMap().get("job");

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runDate", new Date())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            if (!jobExecution.getExitStatus().getExitCode().equals("COMPLETED")) {
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SCHEDULE_ERROR + jobExecution.getStatus());
            }
        } catch (Exception exception) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SCHEDULE_ERROR + exception.getMessage());
        }
    }
}
