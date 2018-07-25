package com.zt.mapper;

import com.zt.entity.Parts;
import com.zt.entity.Type;
import com.zt.entity.TypeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TypeMapper {
    long countByExample(TypeExample example);

    int deleteByExample(TypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeExample example);

    Type selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    List<Type> findByPageWithMaps(Map<String,Object> maps);

    List<Parts> findTypeWithParts(Integer id);
}