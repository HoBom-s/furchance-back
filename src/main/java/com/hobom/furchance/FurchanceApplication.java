package com.hobom.furchance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients
@EnableJpaAuditing
@EnableScheduling
public class FurchanceApplication {

    public static void main(String[] args) {

        SpringApplication.run(FurchanceApplication.class, args);
    }
}
