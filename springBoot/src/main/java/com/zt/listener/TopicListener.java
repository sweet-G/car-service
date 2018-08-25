package com.zt.listener;

import org.springframework.stereotype.Component;


/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Component
public class TopicListener {

   /* @Bean
    public JmsListenerContainerFactory
    topicListenerContainerFactory(ConnectionFactory connectionFactory,
                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        //设置发布订阅模式
        factory.setPubSubDomain(true);
        return factory;
    }*/
}
