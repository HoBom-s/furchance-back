package com.hobom.furchance.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${redis.url}")
    private String redisUrl;

    @Bean
    public RedisAsyncCommands<String, String> connectRedis() {

        RedisClient redisClient = RedisClient.create(redisUrl);

        return redisClient.connect().async();
    }
}
