package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Order;
import com.zt.erp.entity.ServiceType;
import com.zt.erp.entity.Type;
import com.zt.erp.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/3
 */

public interface OrderService {
    /**
     * 查询所有项目类型
     * @return
     */
    List<ServiceType> findAllServiceType();

    /**
     * 获取所有配件类型
     * @return
     */
    List<Type> findAllPartsType();

    /**
     * 新增订单
     * @param orderVo
     */
    void saveOrder(OrderVo orderVo);

    /**
     * 分页
     * @param maps
     * @return
     */
    PageInfo<Order> findPageByParam(Map<String,Object> maps);
}
