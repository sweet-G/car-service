package com.zt.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.tms.entity.StoreAccount;
import com.zt.tms.entity.TicketStore;
import com.zt.tms.entity.TicketStoreExample;
import com.zt.tms.mapper.StoreAccountMapper;
import com.zt.tms.mapper.TicketStoreMapper;
import com.zt.tms.service.TicketStoreService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/9/1
 */
@Service(version = "1.0",timeout = 5000)
public class TicketStoreServiceImpl implements TicketStoreService {

    private Logger logger = LoggerFactory.getLogger(TicketStoreServiceImpl.class);

    @Autowired
    private TicketStoreMapper ticketStoreMapper;
    @Autowired
    private StoreAccountMapper storeAccountMapper;

    /**
     * 新增售票点
     *
     * @param ticketStore
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewTicketStore(TicketStore ticketStore) {
        ticketStore.setCreateTime(new Date());
        ticketStoreMapper.insertSelective(ticketStore);

        //创建售票点账号
        StoreAccount storeAccount = new StoreAccount();
        storeAccount.setTicketStoreId(ticketStore.getId());
        storeAccount.setStoreAccount(ticketStore.getStoreTel());
        //默认密码为000000
        storeAccount.setStorePassword(DigestUtils.md5Hex(StoreAccount.ACCOUNT_INIT_PASSWORD));
        storeAccount.setCreateTime(new Date());
        storeAccount.setStoreState(StoreAccount.ACCOUNT_STATE_NORMAL);

        storeAccountMapper.insertSelective(storeAccount);

        logger.info("新增售票信息:{}" + ticketStore);
    }

    /**
     * 查询所有售票点并分页
     *
     * @param pageNo
     * @param queryParam
     * @return
     */
    @Override
    public PageInfo<TicketStore> findAllTicketStoreByPage(Integer pageNo, Map<String, Object> queryParam) {
        PageHelper.startPage(pageNo,10);

        String storeName = (String) queryParam.get("storeName");
        String storeManager = (String) queryParam.get("storeManager");
        String storeTel = (String) queryParam.get("storeTel");

        TicketStoreExample ticketStoreExample = new TicketStoreExample();
        TicketStoreExample.Criteria criteria = ticketStoreExample.createCriteria();
        if(StringUtils.isNotEmpty(storeName)){
            criteria.andStoreNameLike("%"+ storeName+ "%");
        }
        if(StringUtils.isNotEmpty(storeManager)) {
            criteria.andStoreManagerLike("%"+storeManager+"%");
        }
        if(StringUtils.isNotEmpty(storeTel)) {
            criteria.andStoreTelEqualTo(storeTel);
        }
        ticketStoreExample.setOrderByClause("id desc");

        List<TicketStore> ticketStoreList = ticketStoreMapper.selectByExample(ticketStoreExample);
        return new PageInfo<>(ticketStoreList);
    }

    /**
     * 根据id查找售票点信息
     *
     * @param id
     * @return
     */
    @Override
    public TicketStore findTicketStoreById(Integer id) {
        return ticketStoreMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据售票点id查询该售票点负责人
     *
     * @param id
     * @return
     */
    @Override
    public StoreAccount findStoreAccountById(Integer id) {
        return storeAccountMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改售票点
     *
     * @param ticketStore
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateTicketStore(TicketStore ticketStore) {
         ticketStore.setUpdateTime(new Date());

         //判断是否修改了电话
        StoreAccount storeAccount = storeAccountMapper.selectByPrimaryKey(ticketStore.getId());
        if(!storeAccount.getStoreAccount().equals(ticketStore.getStoreTel())){
            //如果修改了电话，则修改售票点负责人的账户
            storeAccount.setStoreAccount(ticketStore.getStoreTel());
            //重新设置密码
            storeAccount.setStorePassword(DigestUtils.md5Hex(ticketStore.getStoreTel().substring(6)));

            storeAccountMapper.updateByPrimaryKeySelective(storeAccount);
        }

        ticketStoreMapper.updateByPrimaryKeySelective(ticketStore);

        logger.info("修改售票点信息:{}" + ticketStore);
    }

    /**
     * 修改售票点关联账户的状态
     *
     * @param id
     */
    @Override
    public void editState(Integer id) {
        StoreAccount storeAccount = storeAccountMapper.selectByPrimaryKey(id);
        if(storeAccount != null){
            storeAccount.setStoreState(StoreAccount.ACCOUNT_STATE_DISABLE);
            storeAccountMapper.updateByPrimaryKeySelective(storeAccount);
            logger.info("修改售票票点账户状态:{}" + storeAccount);
        }

    }
}
