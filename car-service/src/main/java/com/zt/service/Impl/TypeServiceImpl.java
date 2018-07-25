package com.zt.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.entity.Parts;
import com.zt.entity.Type;
import com.zt.mapper.PartsMapper;
import com.zt.mapper.TypeMapper;
import com.zt.service.TypeService;
import com.zt.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/25
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 分页并模糊
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<Type> findPageWithMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<Type> typeList =typeMapper.findByPageWithMaps(maps);

        PageInfo<Type> pageInfo = new PageInfo<>(typeList);
        return pageInfo;
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delTypeName(Integer id) {
       /* List<Parts> partsList = typeMapper.findTypeWithParts(id);
        if(partsList.size() != 0){
            throw new
        }*/
       Type type = findById(id);
       if(type != null){
           typeMapper.deleteByPrimaryKey(id);
       }

    }

    @Override
    public Type findById(Integer id) {

        Type type = typeMapper.selectByPrimaryKey(id);
        return type;
    }

    /**
     * 新增
     *
     * @param type
     */
    @Override
    public void saveType(Type type) {
        typeMapper.insertSelective(type);
    }
}
