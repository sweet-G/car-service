package com.zt.erp.mapper;

import com.zt.erp.entity.SttleOrderParts;
import com.zt.erp.entity.SttleOrderPartsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SttleOrderPartsMapper {
    long countByExample(SttleOrderPartsExample example);

    int deleteByExample(SttleOrderPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SttleOrderParts record);

    int insertSelective(SttleOrderParts record);

    List<SttleOrderParts> selectByExample(SttleOrderPartsExample example);

    SttleOrderParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SttleOrderParts record, @Param("example") SttleOrderPartsExample example);

    int updateByExample(@Param("record") SttleOrderParts record, @Param("example") SttleOrderPartsExample example);

    int updateByPrimaryKeySelective(SttleOrderParts record);

    int updateByPrimaryKey(SttleOrderParts record);
}