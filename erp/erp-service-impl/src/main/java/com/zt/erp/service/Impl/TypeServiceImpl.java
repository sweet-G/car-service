package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Type;
import com.zt.erp.entity.TypeExample;
import com.zt.erp.mapper.PartsMapper;
import com.zt.erp.mapper.TypeMapper;
import com.zt.erp.service.TypeService;
import com.zt.erp.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/25
 */

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
        Type type = findById(id);
        if(type != null){
            typeMapper.deleteByPrimaryKey(id);
        }

    }

    @Override
    public Type findById(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
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

    /**
     * 根据typeName查找
     *
     * @param addTypeName
     * @return
     */
    @Override
    public Type findByName(String addTypeName) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(addTypeName);
        List<Type> typeList = typeMapper.selectByExample(typeExample);

        Type type = null;
        if(typeList.size() != 0){
            type = typeList.get(0);
        }
        return type;
    }

    /**
     * 修改
     *
     * @param type
     */
    @Override
    public void edit(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

}
