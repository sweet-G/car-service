package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
