package com.hobom.furchance.domain.openapi;

import com.hobom.furchance.domain.openapi.constant.OpenApiConstant;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OpenApiFeignClientRetryer {

    @Bean
    Retryer.Default openFeinClientRetryer() {

        return new Retryer.Default(
                OpenApiConstant.PERIOD,
                TimeUnit.SECONDS.toMillis(OpenApiConstant.DURATION),
                OpenApiConstant.MAX_ATTEMPT
        );
    }
}
