package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.PartsMapper;
import com.zt.erp.mapper.PartsStreamMapper;
import com.zt.erp.mapper.TypeMapper;
import com.zt.erp.service.PartsService;
import com.zt.erp.util.Constant;
import com.zt.erp.vo.FixOrderPartsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    private Logger logger = LoggerFactory.getLogger(PartsServiceImpl.class);

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private PartsStreamMapper partsStreamMapper;

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
    public void update(Parts parts) throws ServiceException{
        partsMapper.updateByPrimaryKeySelective(parts);
    }

    /**
     * 新增
     *
     * @param parts
     */
    @Override
    public void saveParts(Parts parts) {
        if(parts.getInventory() > 0){
            partsMapper.insertSelective(parts);
        }
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

    /**
     * 根据订单id查找配件
     *
     * @param id
     * @return
     */
    @Override
    public List<Parts> findAllOrderWithParts(Integer id) {
        return partsMapper.findOrderAndPartsById(id);
    }

    /**
     * 库存流水
     *
     * @param json
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void subInventory(String json) {
        FixOrderPartsVo fixOrderPartsVo = new Gson().fromJson(json, FixOrderPartsVo.class);

        for(FixOrderParts fixOrderParts : fixOrderPartsVo.getFixOrderPartsList()){
            //更新库存
            Parts parts = partsMapper.selectByPrimaryKey(fixOrderParts.getPartsId());
            parts.setInventory(parts.getInventory() - fixOrderParts.getPartsNum());

            partsMapper.updateByPrimaryKeySelective(parts);

            //新增库存流水
            PartsStream partsStream = new PartsStream();
            partsStream.setPartsId(fixOrderParts.getPartsId());
            partsStream.setNum(fixOrderParts.getPartsNum());
            partsStream.setEmployeeId(fixOrderPartsVo.getEmployeeId());
            partsStream.setOrderId(fixOrderParts.getOrderId());
            partsStream.setType(PartsStream.PARTS_STREAM_TYPE_OUT);

            partsStreamMapper.insertSelective(partsStream);
            logger.info("库存流水:{}",partsStream);
        }

    }


}
