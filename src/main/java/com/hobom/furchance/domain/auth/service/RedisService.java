package com.hobom.furchance.domain.auth.service;

import com.hobom.furchance.config.RedisConfig;
import com.hobom.furchance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisService {

    private final RedisConfig redisConfig;

    @Value("${spring.application.name}")
    private String serviceName;


    private String createRedisKey(User user) {

        return serviceName + "_" + user.getId() + "_" + user.getNickname();
    }

    @Transactional
    public void setRefreshToken(User user, String token) {

        String key = createRedisKey(user);

        redisConfig.connectRedis().setex(key, 60 * 60 * 24, token);
    }

    public String getRefreshToken(User user) {

        String key = createRedisKey(user);

        return redisConfig.connectRedis().get(key);
    }
}
