package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Order;
import com.zt.erp.mapper.OrderMapper;
import com.zt.erp.service.FixService;
import com.zt.erp.util.Constant;
import net.sf.ehcache.search.expression.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/7
 */
@Service
public class FixServiceImpl implements FixService {

    @Autowired
    private OrderMapper orderMapper;
    /**
     * 分页
     *
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<Order> findPageByMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<Order> orderList = orderMapper.findFixWithPage(maps);

        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return pageInfo;

    }
}
