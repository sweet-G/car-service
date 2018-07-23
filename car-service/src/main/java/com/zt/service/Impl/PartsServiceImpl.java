package com.zt.service.Impl;

import com.zt.entity.Parts;
import com.zt.mapper.PartsMapper;
import com.zt.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangtian
 * @date 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsMapper partsMapper;


    @Override
    public Parts findById(Integer id) {
        Parts parts = partsMapper.selectByPrimaryKey(1010);
        return parts;
    }
}
