package com.hobom.furchance.auth.service;

import com.hobom.furchance.config.RedisConfig;
import com.hobom.furchance.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisConfig redisConfig;

    @Value("${spring.application.name}")
    private String serviceName;

    private String createRedisKey(User user) {

        return serviceName + "_" + user.getId() + "_" + user.getNickname();
    }

    public void setRefreshToken(User user, String token) {

        String key = createRedisKey(user);

        redisConfig.connectRedis().setnx(key, token);
        redisConfig.connectRedis().expire(key, 60 * 60 * 24); // 1day
    }

    public String getRefreshToken(User user) {

        String key = createRedisKey(user);

        return redisConfig.connectRedis().get(key);
    }
}
