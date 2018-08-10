package com.zt.erp.mq;

import com.google.gson.Gson;
import com.zt.erp.service.FixOrderService;
import com.zt.erp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author zhangtian
 * @date 2018/8/9
 */

public class OrderStateListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(OrderStateListener.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            String json = textMessage.getText();
            logger.info("接收消息：{}",json);
            orderService.editOrderState(json);

            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
