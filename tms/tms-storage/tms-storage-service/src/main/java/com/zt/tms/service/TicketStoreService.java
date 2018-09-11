package com.zt.tms.service;

import com.zt.tms.entity.TicketStore;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/9/11
 */

public interface TicketStoreService {
    /**
     * 查询所有售票点
     * @return
     */
    List<TicketStore> findAllTicketStore();

}
