package com.zt.config;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Component
public class RedisProperties {

    private Map<String,Long> expires;

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }
}
