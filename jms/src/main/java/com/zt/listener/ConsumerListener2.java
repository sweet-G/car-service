package com.zt.listener;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author zhangtian
 * @date 2018/8/7
 */
@Component
public class ConsumerListener2 implements SessionAwareMessageListener {

    public void onMessage(Message message, Session session) throws JMSException {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("&&&& -> " + textMessage.getText());
            if(textMessage.getText().equals("spring-queue-01")) {
                throw new RuntimeException();
            }
            textMessage.acknowledge();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.recover();
        }
    }
}

