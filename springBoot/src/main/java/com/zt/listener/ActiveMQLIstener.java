package com.zt.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Component
public class ActiveMQLIstener {

    private Logger logger = LoggerFactory.getLogger(ActiveMQLIstener.class);

    @JmsListener(destination = "springboot-queue")
    public void queueMessage(String message){
        logger.info("监听queue:{}",message);
    }

    @JmsListener(destination = "springboot-topic",
            containerFactory = "topicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        logger.info("监听topic:{}",message);
    }


}
