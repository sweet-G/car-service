package com.zt.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.entity.Parts;
import com.zt.entity.PartsExample;
import com.zt.entity.Type;
import com.zt.entity.TypeExample;
import com.zt.mapper.PartsMapper;
import com.zt.mapper.TypeMapper;
import com.zt.service.PartsService;
import com.zt.util.Constant;
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
    public boolean findByPartsNo(String partNo) {
        Parts parts = partsMapper.selectPartsNo(partNo);

        if(parts == null){
            return true;
        }
        return false;
    }
}
