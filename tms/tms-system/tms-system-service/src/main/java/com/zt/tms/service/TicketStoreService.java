package com.zt.tms.service;

import com.github.pagehelper.PageInfo;
import com.zt.tms.entity.StoreAccount;
import com.zt.tms.entity.TicketStore;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/9/1
 */

public interface TicketStoreService {
    /**
     * 新增售票点
     * @param ticketStore
     */
    void saveNewTicketStore(TicketStore ticketStore);

    /**
     * 查询所有售票点并分页
     * @param pageNo
     * @param queryParam
     * @return
     */
    PageInfo<TicketStore> findAllTicketStoreByPage(Integer pageNo, Map<String,Object> queryParam);

    /**
     * 根据id查找售票点信息
     * @param id
     * @return
     */
    TicketStore findTicketStoreById(Integer id);

    /**
     * 根据售票点id查询该售票点负责人
     * @param id
     * @return
     */
    StoreAccount findStoreAccountById(Integer id);

    /**
     * 修改售票点
     * @param ticketStore
     */
    void updateTicketStore(TicketStore ticketStore);
}
