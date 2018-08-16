package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.SttleOrder;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/15
 */

public interface SttleOrderService {
    /**
     * 查询结算订单并分页
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<SttleOrder> findSttlePageByMap(Integer pageNo, Map<String,Object> maps);
}
