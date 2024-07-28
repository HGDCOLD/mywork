package com.cj.properties;

import lombok.Data;

@Data
public class RedisPoolProperties {

    private int size;

    private int connectionTimeout;
    private int maxIdle;
    private int minIdle;
    private int maxActive;
    private int maxWait;
}
