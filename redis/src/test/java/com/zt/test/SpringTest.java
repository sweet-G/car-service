package com.zt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zhangtian
 * @date 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class SpringTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void setValue(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","jack");

        jedis.close();
    }

    @Test
    public void getValue(){
        Jedis jedis = jedisPool.getResource();
        String name = jedis.get("name");

        System.out.println(name);
        jedis.close();
    }
}
