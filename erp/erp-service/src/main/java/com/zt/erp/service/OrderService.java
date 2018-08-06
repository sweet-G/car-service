package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Order;
import com.zt.erp.entity.Parts;
import com.zt.erp.entity.ServiceType;
import com.zt.erp.entity.Type;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.vo.OrderVo;

import java.rmi.server.ServerCloneException;
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

    /**
     *根据id查询订单的详情
     * @param id
     * @return
     */
    Order findOrderWithCarAndCustomerById(Integer id) throws ServiceException;

    /**
     * 根据id查询订单的配件
     * @param id
     * @return
     */
    List<Parts> findOrderWithPartsById(Integer id);

    /**
     * 根据id查询项目类型
     * @param id
     * @return
     */
    ServiceType findOrderWithServiceTypeById(Integer id);

    /**
     * 删除订单
     * @param id
     */
    void del(Integer id);

    /**
     * 订单下发
     * @param id
     */
    void findOrderWithTransById(Integer id) throws ServiceException;

    /**
     * 修改订单
     * @param orderVo
     */
    void edit(OrderVo orderVo)  throws ServiceException;
}
