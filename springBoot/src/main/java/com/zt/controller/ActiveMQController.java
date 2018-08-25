package com.zt.controller;

import org.springframework.stereotype.Controller;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Controller
public class ActiveMQController {

   /* @Autowired
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
    }*/

}
