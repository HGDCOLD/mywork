package com.cj.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "myredis")
@Data
public class RedisProperties {

    private int database;
    private String password;

    private int timeout;

    private RedisPoolProperties pool;

    private RedisSingleProperties single;
}
