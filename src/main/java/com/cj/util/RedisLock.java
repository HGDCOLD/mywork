package com.cj.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    public void tryLock()


}
