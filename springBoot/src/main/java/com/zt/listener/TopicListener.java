package com.zt.listener;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Component
public class TopicListener {

    @Bean
    public JmsListenerContainerFactory
    topicListenerContainerFactory(ConnectionFactory connectionFactory,
                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        //设置发布订阅模式
        factory.setPubSubDomain(true);
        return factory;
    }
}
