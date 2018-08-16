package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.SttleOrder;
import com.zt.erp.mapper.SttleOrderMapper;
import com.zt.erp.mapper.SttleOrderPartsMapper;
import com.zt.erp.service.SttleOrderService;
import com.zt.erp.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/15
 */
@Service
public class SttleOrderServiceImpl implements SttleOrderService {

    @Autowired
    private SttleOrderMapper sttleOrderMapper;
    @Autowired
    private SttleOrderPartsMapper sttleOrderPartsMapper;

    /**
     * 查询结算订单并分页
     *
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<SttleOrder> findSttlePageByMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<SttleOrder> sttleOrderList = sttleOrderMapper.findPageByMap(maps);

        PageInfo<SttleOrder> pageInfo = new PageInfo<>(sttleOrderList);
        return pageInfo;
    }
}
