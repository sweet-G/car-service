package com.zt.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.entity.Parts;
import com.zt.mapper.PartsMapper;
import com.zt.service.PartsService;
import com.zt.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 分页查询Parts集合
     *
     * @return PageInfo<Parts>
     */
    @Override
    public PageInfo<Parts> findPage(Integer pageNo) {
        //分页
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<Parts> partsList = partsMapper.findPartsWithType();
        //封装分页对象
        PageInfo<Parts> pageInfo = new PageInfo<>(partsList);
        return pageInfo;
    }
}
