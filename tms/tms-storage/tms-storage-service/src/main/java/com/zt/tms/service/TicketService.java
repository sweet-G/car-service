package com.zt.tms.service;

import com.github.pagehelper.PageInfo;
import com.zt.tms.entity.Account;
import com.zt.tms.entity.TicketInRecord;
import com.zt.tms.entity.TicketOutRecord;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/9/3
 */

public interface TicketService {
    /**
     * 入库
     * @param ticketInRecord
     * @param account
     */
    void saveStorage(TicketInRecord ticketInRecord, Account account);

    /**
     * 入库首页
     * @param pageNo
     * @return
     */
    PageInfo<TicketInRecord> findTicketRecordByPageNo(Integer pageNo);

    /**
     * 根据id删除年票
     * @param id
     */
    void delInRecordById(Integer id);

    /**
     * 出库首页
     * @param pageNo
     * @return
     */
    PageInfo<TicketOutRecord> findTicketOutRecordByPageNo(Integer pageNo);

    /**
     * 出库记录
     * @param ticketOutRecord
     * @param account
     */
    void saveTicketOutRecord(TicketOutRecord ticketOutRecord, Account account);

    /**
     * 删除下发年票
     * @param id
     */
    void delOutRecordById(Integer id);

    /**
     * 统计年票
     * @return
     */
    Map<String,Long> countTicketByState();
}
