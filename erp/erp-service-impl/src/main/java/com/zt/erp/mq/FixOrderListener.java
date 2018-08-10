package com.zt.erp.mq;

import com.zt.erp.service.FixOrderService;
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

public class FixOrderListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(FixOrderListener.class);

    @Autowired
    private FixOrderService fixOrderService;
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            String json = textMessage.getText();
            logger.info("接收json: {}",json);
            fixOrderService.createFixOrder(json);

            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
