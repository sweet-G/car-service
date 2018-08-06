package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Parts;
import com.zt.erp.entity.PartsExample;
import com.zt.erp.entity.Type;
import com.zt.erp.entity.TypeExample;
import com.zt.erp.mapper.PartsMapper;
import com.zt.erp.mapper.TypeMapper;
import com.zt.erp.service.PartsService;
import com.zt.erp.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Parts findById(Integer id) {
        Parts parts = partsMapper.selectByPrimaryKey(id);
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

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public void del(Integer id) {
        Parts parts = findById(id);
        if(parts != null){
            partsMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Type> findTypeList() {
        TypeExample typeExample = new TypeExample();

        return typeMapper.selectByExample(typeExample);
    }

    /**
     * parts模糊查询
     *
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<Parts> findPageWithTypeMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<Parts> partsList = partsMapper.findPageWithTypeMap(maps);

        PageInfo<Parts> pageInfo = new PageInfo<>(partsList);
        return pageInfo;
    }

    /**
     * 根据id更新
     * @param parts
     */
    @Override
    public void update(Parts parts) {
        partsMapper.updateByPrimaryKeySelective(parts);
    }

    /**
     * 新增
     *
     * @param parts
     */
    @Override
    public void saveParts(Parts parts) {
       partsMapper.insertSelective(parts);
    }

    /**
     * 验证partsNo是否重复
     *
     * @param partNo
     */
    @Override
    public Parts findByPartsNo(String partNo) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andPartsNoEqualTo(partNo);
        List<Parts> partsList =  partsMapper.selectByExample(partsExample);

        Parts parts = null;
        if(partsList.size() != 0){
            parts = partsList.get(0);
        }
        return parts;
    }

    /**
     * 根据id查找parts中的type
     *
     * @param id
     * @return
     */
    @Override

    public List<Parts> findByTypeId(Integer id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);

        return partsMapper.selectByExample(partsExample);
    }

    /**
     * 根据id查找配件类型
     *
     * @param id
     * @return
     */
    @Override
    public List<Parts> findPartsByType(Integer id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        return partsMapper.selectByExample(partsExample);
    }
}
