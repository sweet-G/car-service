package com.zt.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Controller
public class ActiveMQController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/queue")
    @ResponseBody
    public String queue(){
        ActiveMQQueue queue = new ActiveMQQueue("springboot-queue");
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello-queue");
            }
        });

        return "";
    }

    @GetMapping("/topic")
    @ResponseBody
    public void sendMessageToTopic() {
        ActiveMQTopic activeMQTopic = new ActiveMQTopic("springboot-topic");
        jmsTemplate.send(activeMQTopic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello");
            }
        });
    }

}
