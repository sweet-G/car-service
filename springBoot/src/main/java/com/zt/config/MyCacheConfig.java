package com.zt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Configuration
//@EnableCaching
public class MyCacheConfig {

   /* @Bean
    public CacheManager cacheConfig() {
        return new EhCacheCacheManager();
    }*/

    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setExpires(redisProperties.getExpires());
        return cacheManager;
    }
}
