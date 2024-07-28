package com.cj.config;

import com.cj.properties.RedisProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class redisConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient1(){
        Config config = new Config();
        String address1 = redisProperties.getSingle().getAddress1();
        address1 = address1.startsWith("redis://") ?   address1 : "redis://" + address1;
        config.useSingleServer()
                .setAddress(address1)
                .setTimeout(redisProperties.getPool().getConnectionTimeout())
                .setConnectionPoolSize(redisProperties.getPool().getSize())
                .setConnectionMinimumIdleSize(0);
        System.out.println("密码:" + redisProperties.getPassword());
        if(StringUtils.isNotBlank(redisProperties.getPassword())){
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
    @Bean
    public RedissonClient redissonClient2(){
        Config config = new Config();
        String address2 = redisProperties.getSingle().getAddress2();
        address2 = address2.startsWith("redis://") ?   address2 : "redis://" + address2;
        config.useSingleServer()
                .setAddress(address2)
                .setTimeout(redisProperties.getPool().getConnectionTimeout())
                .setConnectionPoolSize(redisProperties.getPool().getSize())
                .setConnectionMinimumIdleSize(0);
        System.out.println("密码:" + redisProperties.getPassword());
        if(StringUtils.isNotBlank(redisProperties.getPassword())){
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
