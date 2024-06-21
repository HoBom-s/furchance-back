package com.hobom.furchance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FurchanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurchanceApplication.class, args);
    }

}
