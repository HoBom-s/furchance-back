package com.hobom.furchance.openapi;

import com.hobom.furchance.constant.OpenApiConstant;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

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
