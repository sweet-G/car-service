package com.zt.erp.mapper;

import com.zt.erp.entity.CountDaliy;
import com.zt.erp.entity.CountDaliyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountDaliyMapper {
    long countByExample(CountDaliyExample example);

    int deleteByExample(CountDaliyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CountDaliy record);

    int insertSelective(CountDaliy record);

    List<CountDaliy> selectByExample(CountDaliyExample example);

    CountDaliy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CountDaliy record, @Param("example") CountDaliyExample example);

    int updateByExample(@Param("record") CountDaliy record, @Param("example") CountDaliyExample example);

    int updateByPrimaryKeySelective(CountDaliy record);

    int updateByPrimaryKey(CountDaliy record);
}