package com.zt.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zt.tms.entity.TicketStore;
import com.zt.tms.entity.TicketStoreExample;
import com.zt.tms.mapper.TicketStoreMapper;
import com.zt.tms.service.TicketStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/9/11
 */
@Service(version = "1.0",timeout = 5000)
public class TicketStoreServiceImpl implements TicketStoreService {

    private Logger logger = LoggerFactory.getLogger(TicketStoreServiceImpl.class);

    @Autowired
    private TicketStoreMapper ticketStoreMapper;

    /**
     * 查询所有售票点
     *
     * @return
     */
    @Override
    public List<TicketStore> findAllTicketStore() {
        return ticketStoreMapper.selectByExample(new TicketStoreExample());
    }
}
