package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zt.erp.dto.OrderInfoDto;
import com.zt.erp.dto.OrderStateDto;
import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.*;
import com.zt.erp.service.OrderService;
import com.zt.erp.util.Constant;
import com.zt.erp.vo.OrderVo;
import com.zt.erp.vo.PartsVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/3
 */
@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    JmsTemplate jmsTemplate;


    /**
     * 查询所有项目类型
     *
     * @return
     */
    @Override
    public List<ServiceType> findAllServiceType() {
        ServiceTypeExample serviceTypeExample = new ServiceTypeExample();
        return serviceTypeMapper.selectByExample(serviceTypeExample);
    }

    /**
     * 获取所有配件类型
     *
     * @return
     */
    @Override
    public List<Type> findAllPartsType() {
        TypeExample typeExample = new TypeExample();
        return typeMapper.selectByExample(typeExample);
    }

    /**
     * 新增订单
     *
     * @param orderVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrder(OrderVo orderVo) {
        //新增订单表
        Order order = new Order();
        order.setCarId(orderVo.getCarId());
        order.setFee(orderVo.getFee());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setState(Order.ORDER_STATE_NEW);

        orderMapper.insertSelective(order);
        logger.info("新增订单：{}",order);

        //新增订单配件关联关系表
        for(PartsVo vo : orderVo.getPartsLists()){
            OrderParts orderParts = new OrderParts();
            orderParts.setOrderId(order.getId());
            orderParts.setPartsId(vo.getId());
            orderParts.setNum(vo.getNum());

            orderPartsMapper.insertSelective(orderParts);
            logger.info("新增订单配件：{}",orderParts);
        }

        //获取当前登录对象(下单人)
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        //新增订单与当前对象的关联关系表
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setOrderId(order.getId());
        orderEmployee.setEmployeeId(employee.getId());

        orderEmployeeMapper.insertSelective(orderEmployee);
        logger.info("新增下单对象：{}",orderEmployee);

    }

    /**
     * 分页
     *
     * @param maps
     * @return
     */
    @Override
    public PageInfo<Order> findPageByParam(Map<String, Object> maps) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(maps.get("pageNo"))),Constant.PAGE_SIZE);

        List<Order> orderList = orderMapper.findUndonePageByParam(maps);

        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 根据id查询订单的详情
     *
     * @param id
     * @return
     */
    @Override
    public Order findOrderWithCarAndCustomerById(Integer id) throws ServiceException{
       Order order = orderMapper.findCarAndCustomerById(id);
        if(order == null) {
            throw new ServiceException("参数异常或者订单不存在");
        }
        return order;
    }

    /**
     * 根据id查询订单的配件
     *
     * @param id
     * @return
     */
    @Override
    public List<Parts> findOrderWithPartsById(Integer id) {
        List<Parts> partsList = partsMapper.findOrderAndPartsById(id);
        return partsList;
    }

    /**
     * 根据id查询项目类型
     *
     * @param id
     * @return
     */
    @Override
    public ServiceType findOrderWithServiceTypeById(Integer id) {
        return serviceTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void del(Integer id) {
        //删除订单员工关联表
        OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
        orderEmployeeExample.createCriteria().andOrderIdEqualTo(id);
        orderEmployeeMapper.deleteByExample(orderEmployeeExample);

        //删除订单配件关联表
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(id);
        orderPartsMapper.deleteByExample(orderPartsExample);

        //删除订单表
        orderMapper.deleteByPrimaryKey(id);
        logger.info("删除订单：{}",id);
    }

    /**
     * 订单下发
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void findOrderWithTransById(Integer id) throws ServiceException{
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null) {
            throw new ServiceException("参数异常或订单不存在");
        }

        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new ServiceException(("该订单已经生成并下发，操作失败"));
        }
        //设置订单下发状态
        order.setState(Order.ORDER_STATE_TRANS);
        orderMapper.updateByPrimaryKeySelective(order);

        //将订单放到消息队列中
        sendOrderInfoMq(id);
    }

    /**
     * 订单下发放到消息队列中
     * @param id
     */
    private void sendOrderInfoMq(Integer id) {
        //获得订单信息
        Order order = orderMapper.findCarAndCustomerById(id);
        //获得项目类型信息
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(order.getServiceTypeId());
        //获得订单配件信息
        List<Parts> partsList = partsMapper.findOrderAndPartsById(id);

        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setOrder(order);
        orderInfoDto.setServiceType(serviceType);
        orderInfoDto.setPartsList(partsList);

        //专程json发送到消息队列中
        String json = new Gson().toJson(orderInfoDto);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });

    }
    /**
     * 修改订单
     *
     * @param orderVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(OrderVo orderVo) throws ServiceException{
        // 更新订单
        Order order = orderMapper.selectByPrimaryKey(orderVo.getId());

        if(order == null) {
            throw new ServiceException("参数异常或者订单不存在");
        }

        // 只有状态为1的订单可以被修改
        if(!order.getState().equals(Order.ORDER_STATE_NEW)) {
            throw new ServiceException("该订单已生成，不能修改");
        }

        order.setFee(orderVo.getFee());
        order.setCarId(orderVo.getCarId());
        order.setServiceTypeId(orderVo.getServiceTypeId());

        orderMapper.updateByPrimaryKeySelective(order);

        // 删除原有的关联关系
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(orderVo.getId());
        orderPartsMapper.deleteByExample(orderPartsExample);

        // 新增订单和配件关系表
        List<PartsVo> partsVoList = orderVo.getPartsLists();
        addOrderParts(order.getId(), partsVoList);

        logger.info("更新订单{}", order.getId());
    }

    /**
     * @return
     */
    @Override
    public List<Order> findAllOrder() {
        OrderExample orderExample = new OrderExample();
        return orderMapper.selectByExample(orderExample);
    }

    /**
     * 根据消息队列json修改order状态
     *
     * @param json
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrderState(String json) {

        OrderStateDto orderStateDto = new Gson().fromJson(json, OrderStateDto.class);
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());

        if(order == null){
            logger.error("{} 订单不存在", orderStateDto.getOrderId());
        }

        //更改基础表的状态
        order.setState(orderStateDto.getState());
        orderMapper.updateByPrimaryKeySelective(order);

        //新增订单与员工
        // 如果员工的employeeId==null 则代表员工订单关联关系不需要新增
        if (orderStateDto.getEmployeeId() != null) {
            OrderEmployee orderEmployee = new OrderEmployee();
            orderEmployee.setEmployeeId(orderStateDto.getEmployeeId());
            orderEmployee.setOrderId(order.getId());

            orderEmployeeMapper.insertSelective(orderEmployee);
        }

    }


    /**
     * 新增订单配件关联表
     * @param orderId 订单id
     * @param partsVoList 配件集合
     */
    private void addOrderParts(Integer orderId, List<PartsVo> partsVoList) {
        for(PartsVo partsVo : partsVoList) {
            OrderParts orderParts = new OrderParts();
            orderParts.setOrderId(orderId);
            orderParts.setPartsId(partsVo.getId());
            orderParts.setNum(partsVo.getNum());

            orderPartsMapper.insertSelective(orderParts);
        }

    }


}
