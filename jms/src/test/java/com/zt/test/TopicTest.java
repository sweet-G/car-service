package com.zt.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhangtian
 * @date 2018/8/7
 */

public class TopicTest {

    @org.junit.Test
    public void producerMessage() throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            //1. 创建工厂连接
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            //2. 创建连接，并开启
            connection = connectionFactory.createConnection();
            connection.start();
            //3. 创建session，是否开启手动提交，签收模式：AUTO_ACKNOWLEDGE（自动）CLIENT_ACKNOWLEDGE(手动)
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //4. 创建消息目的地
            Destination destination = session.createTopic("HelloWorld-Topic");
            //5. 创建生产者
            producer = session.createProducer(destination);
            //6. 发送消息
            TextMessage textMessage = session.createTextMessage("hello-jms-05");
            producer.send(textMessage);

        } catch (JMSException e) {
        } finally {
            //7. 关闭资源
            producer.close();
            session.close();
            connection.close();
        }
    }

    @org.junit.Test
    public void consumerMessage() throws Exception{
        //1. 创建工厂连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2. 创建连接，并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3. 创建session，是否开启手动提交，签收模式：AUTO_ACKNOWLEDGE（自动）CLIENT_ACKNOWLEDGE(手动)
        final Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4. 创建消息目的地
        Destination destination = session.createTopic("HelloWorld-Topic");
        //5. 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //6. 接收消息
       consumer.setMessageListener(new MessageListener() {
           public void onMessage(Message message) {
               TextMessage textMessage = (TextMessage) message;
               try {
                   System.out.print(textMessage.getText());

               } catch (JMSException e) {
                   e.printStackTrace();
               }
           }
       });
        //一直监听生产者
       System.in.read();
       //7. 释放资源
        consumer.close();
        session.close();
        connection.close();
    }
}
