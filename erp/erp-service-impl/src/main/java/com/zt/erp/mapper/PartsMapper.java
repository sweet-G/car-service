package com.zt.erp.mapper;

import com.zt.erp.entity.Parts;
import com.zt.erp.entity.PartsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    List<Parts> findPartsWithType();

    Parts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);

    List<Parts> findPageWithTypeMap(Map<String, Object> maps);

    Parts selectPartsNo(String partNo);

    List<Parts> findOrderAndPartsById(Integer id);
}