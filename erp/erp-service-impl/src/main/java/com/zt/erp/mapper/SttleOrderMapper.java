package com.zt.erp.mapper;

import com.zt.erp.entity.SttleOrder;
import com.zt.erp.entity.SttleOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SttleOrderMapper {
    long countByExample(SttleOrderExample example);

    int deleteByExample(SttleOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(SttleOrder record);

    int insertSelective(SttleOrder record);

    List<SttleOrder> selectByExample(SttleOrderExample example);

    SttleOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") SttleOrder record, @Param("example") SttleOrderExample example);

    int updateByExample(@Param("record") SttleOrder record, @Param("example") SttleOrderExample example);

    int updateByPrimaryKeySelective(SttleOrder record);

    int updateByPrimaryKey(SttleOrder record);

    List<SttleOrder> findPageByMap(Map<String,Object> maps);
}