package com.zt.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Configuration
public class RedissionConfig {
    @Autowired
    private RedissionConfigProperty redissionConfigProperty;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redissionConfigProperty.getHost())
                .setTimeout(redissionConfigProperty.getTimeout());
        return Redisson.create(config);
    }
}
