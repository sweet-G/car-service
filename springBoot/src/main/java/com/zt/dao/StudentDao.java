package com.zt.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author zhangtian
 * @date 2018/8/23
 */
@Repository
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(){
        String sql = "select count(*) from student";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }
}
