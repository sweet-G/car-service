package com.zt.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangtian
 * @date 2018/8/8
 */
@Component
public class ConsumerAnnotationListener {

    @JmsListener(destination = "spring-queue")
    public void getMessage(String message){
        System.out.println(message);
    }
}
