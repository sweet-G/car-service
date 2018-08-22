package com.zt.test;

import com.alibaba.fastjson.JSON;
import com.zt.entity.User;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zhangtian
 * @date 2018/8/21
 */

public class RedisTest {

    @Test
    public void testString(){
        Jedis jedis = new Jedis("192.168.1.143",6379);
        jedis.set("name","jack");
        jedis.close();
    }

    @Test
    public void testPool(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(5);

        JedisPool jedisPool = new JedisPool(config,"192.168.1.143",6379);

        Jedis jedis = jedisPool.getResource();
        jedis.set("address","jiaozuo");

        jedis.close();
        jedisPool.destroy();
    }

    @Test
    public void testUser(){
        Jedis jedis = new Jedis("192.168.1.143",6379);

        // 取值
        String json = jedis.get("user:1000");
        User user = JSON.parseObject(json,User.class);
        System.out.println(user);

        /*//存值
        User user = new User();
        user.setId(1000);
        user.setUserName("alex");
        user.setAddress("杭州");

        String json = JSON.toJSONString(user);
        jedis.set("user:1000",json);*/
        jedis.close();
    }
}
