package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Order;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/7
 */

public interface FixService {
    /**
     *分页
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<Order> findPageByMap(Integer pageNo, Map<String,Object> maps);
}
