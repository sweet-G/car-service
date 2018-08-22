package com.zt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangtian
 * @date 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redission.xml")
public class SpringRedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testvalue(){
        RBucket<String> rBucket = redissonClient.getBucket("user:1003");
        rBucket.set("alex");
    }
}
