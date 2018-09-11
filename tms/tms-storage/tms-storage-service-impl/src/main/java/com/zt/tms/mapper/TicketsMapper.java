package com.zt.tms.mapper;

import com.zt.tms.entity.Tickets;
import com.zt.tms.entity.TicketsExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TicketsMapper {
    long countByExample(TicketsExample example);

    int deleteByExample(TicketsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Tickets record);

    int insertSelective(Tickets record);

    List<Tickets> selectByExample(TicketsExample example);

    Tickets selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Tickets record, @Param("example") TicketsExample example);

    int updateByExample(@Param("record") Tickets record, @Param("example") TicketsExample example);

    int updateByPrimaryKeySelective(Tickets record);

    int updateByPrimaryKey(Tickets record);

    void batchInsert(@Param("ticketsList") List<Tickets> ticketsList);

    List<Tickets> findByBeginNumAndEndNumAndState(@Param("beginNum") String beginNum,
                                                  @Param("endNum") String endNum,
                                                  @Param("state") String state);

    void batchDeleteById(@Param("delList") List<Long> delList);

    List<Tickets> findByBeginNumAndEndNum(@Param("beginNum") String beginNum,
                                          @Param("endNum") String endNum);

    Map<String,Long> countByState();
}