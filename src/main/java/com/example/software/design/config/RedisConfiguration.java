package com.example.software.design.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching
@EnableRedisRepositories(basePackages = "com.example.software.design.repository.redis")
public class RedisConfiguration {

}
