package com.zt.erp.mapper;

import com.zt.erp.entity.FixOrder;
import com.zt.erp.entity.FixOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface FixOrderMapper {
    long countByExample(FixOrderExample example);

    int deleteByExample(FixOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(FixOrder record);

    int insertSelective(FixOrder record);

    List<FixOrder> selectByExample(FixOrderExample example);

    FixOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") FixOrder record, @Param("example") FixOrderExample example);

    int updateByExample(@Param("record") FixOrder record, @Param("example") FixOrderExample example);

    int updateByPrimaryKeySelective(FixOrder record);

    int updateByPrimaryKey(FixOrder record);

    List<FixOrder> findPageByMaps(Map<String,Object> maps);

    List<FixOrder> findListParts();

    FixOrder findfixOrderPartsById(Integer id);
}