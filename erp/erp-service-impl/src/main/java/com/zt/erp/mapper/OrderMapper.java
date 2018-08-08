package com.zt.erp.mapper;

import com.zt.erp.entity.Order;
import com.zt.erp.entity.OrderExample;
import java.util.List;
import java.util.Map;

import com.zt.erp.entity.Parts;
import com.zt.erp.entity.ServiceType;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> findUndonePageByParam(Map<String,Object> maps);

    Order findCarAndCustomerById(Integer id);

    List<Parts> findOrderAndPartsById(Integer id);

    ServiceType findOrderAndServiceTypeById(Integer id);

    List<Order> findFixWithPage(Map<String,Object> maps);
}