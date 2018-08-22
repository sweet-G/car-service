package com.zt.test;

import com.zt.entity.User;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author zhangtian
 * @date 2018/8/21
 */
public class RedissonTest {

    @Test
    public void testRedisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.143:6379");

        RedissonClient redissonClient = Redisson.create(config);

        RBucket<String> rBucket = redissonClient.getBucket("user:1001");
        rBucket.set("tom");

        RBucket<User> userRBucket = redissonClient.getBucket("user:1002");
        User user = new User();
        user.setId(1002);
        user.setUserName("rose");
        user.setAddress("hangzhou");
        userRBucket.set(user);
    }

    @Test
    public void getRedisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.143:6379");

        RedissonClient redissonClient = Redisson.create(config);

        RBucket<String> rBucket = redissonClient.getBucket("user:1001");
        String name = rBucket.get();

        System.out.println(name);
    }
}
