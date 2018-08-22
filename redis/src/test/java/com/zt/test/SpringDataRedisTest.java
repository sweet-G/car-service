package com.zt.test;

import com.zt.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangtian
 * @date 2018/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-data-redis.xml")
public class SpringDataRedisTest {
    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        //this.redisTemplate.setValueSerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    }

    @Test
    public void testSetValue(){
        User user = new User();
        user.setId(1000);
        user.setUserName("丽丽");
        user.setAddress("上海");

        redisTemplate.opsForValue().set("user:1000",user);
    }

    @Test
    public void getUserFromRedis() {
        User user = redisTemplate.opsForValue().get("user:1000");
        System.out.println(user);
    }

   /* @Test
    public void testString(){
        ValueOperations<String, String> stringUserValueOperations = redisTemplate.opsForValue();
        stringUserValueOperations.set("username:1002","王五");
    }*/

}
