package com.zt.erp.mq;

import com.zt.erp.service.PartsService;
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

public class OrderPartsListener implements SessionAwareMessageListener {
    private Logger logger = LoggerFactory.getLogger(OrderPartsListener.class);

    @Autowired
    private PartsService partsService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            // 获得队列中的json数据
            String json = textMessage.getText();
            logger.info("接收的队列json数据:{}", json);
            partsService.subInventory(json);

            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
