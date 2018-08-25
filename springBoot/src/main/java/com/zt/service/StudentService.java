package com.zt.service;

import com.zt.entity.Student;
import com.zt.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Cacheable
    public Student findById(Integer id){
        return studentMapper.selectByPrimaryKey(id);
    }

    @CacheEvict
    public void delById(Integer id) {

    }
}
