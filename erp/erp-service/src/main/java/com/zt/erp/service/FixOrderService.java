package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Employee;
import com.zt.erp.entity.FixOrder;
import com.zt.erp.entity.FixOrderParts;
import com.zt.erp.entity.Order;
import com.zt.erp.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/9
 */

public interface FixOrderService {
    /**
     * 根据消息队列的json新增fixOrder
     * @param json
     */
    void createFixOrder(String json);

    /**
     * 分页
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<FixOrder> findPageByMap(Integer pageNo, Map<String,Object> maps);

    /**
     * 查询parts
     * @return
     */
    List<FixOrder> findFixOrderListWithParts();

    /**
     * 根据订单id和当前登录员工领取维修任务
     * @param id
     * @param employee
     * @throws ServiceException
     */
    void taskReceive(Integer id, Employee employee) throws ServiceException;

    /**
     * 根据id查找fixOrder
     * @param id
     * @return
     */
    FixOrder findFixOrderById(Integer id);

    /**
     * 完成任务
     * @param id
     */
    void taskDone(Integer id);

    /**
     * 查询质检单
     * @return
     */
    List<FixOrder> findCheckOrderListWithParts();

    /**
     *根据订单id和当前登录员工领取质检任务
     * @param id
     * @param employee
     */
    void checkReceiveTask(Integer id, Employee employee);

    /**
     * 根据id查询质检订单
     * @param id
     * @return
     */
    FixOrder findCheckOrderById(Integer id);

    /**
     * 完成质检任务
     * @param id
     */
    void CheckTaskDone(Integer id);

    /**
     * 添加超时任务
     * @param jobName
     */
    void addFixTimeOut(String jobName);
}
