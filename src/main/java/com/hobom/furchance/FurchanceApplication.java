package com.hobom.furchance;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableScheduling
public class FurchanceApplication {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job openApiJob;

    public static void main(String[] args) {
        SpringApplication.run(FurchanceApplication.class, args);
    }

/*
 implements CommandLineRunner
    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(openApiJob, new JobParametersBuilder().addDate("run.date", new Date()).toJobParameters());
    }*/
}
