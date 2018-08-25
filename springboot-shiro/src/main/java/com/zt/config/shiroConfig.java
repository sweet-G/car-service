package com.zt.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zt.shiro.ShiroRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zhangtian
 * @date 2018/8/25
 */
@Configuration
@ControllerAdvice
public class shiroConfig {

    @ExceptionHandler(AuthenticationException.class)
    public String exception(){
        return "error/401";
    }

    @Bean
    public Realm realm() {
        return new ShiroRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/favicon.ico","anon");
        chainDefinition.addPathDefinition("/static/**","anon");
        chainDefinition.addPathDefinition("/logout","logout");
        chainDefinition.addPathDefinition("/**","user");
        return chainDefinition;
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
