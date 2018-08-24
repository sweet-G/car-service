package com.zt.controller;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Controller
public class SpringDataRedisController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/redisson")
    @ResponseBody
    public String redisson(){
        RBucket<String> rBucket = redissonClient.getBucket("userName");
        rBucket.set("nihao");
        /*if(rBucket.isExists()) {
            return rBucket.get();
        }*/
        return "nothing";
    }


    /*private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    @GetMapping("/redis")
    @ResponseBody
    public String redis(){
        redisTemplate.opsForValue().set("name","lili");
        return "success";
    }*/
}
