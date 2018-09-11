package com.zt.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zt.tms.entity.*;
import com.zt.tms.mapper.AccountLoginLogMapper;
import com.zt.tms.mapper.AccountMapper;
import com.zt.tms.mapper.AccountRolesMapper;
import com.zt.tms.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/30
 */
@Service(version = "1.0",timeout = 5000)
public class AccountServiceImpl implements AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;
    @Autowired
    private AccountRolesMapper accountRolesMapper;

    /**
     * 根据姓名查找对象
     *
     * @param username
     * @return
     */
    @Override
    public Account findAccountMoblie(String username) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(username);

        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(accountList != null & accountList.size() > 0){
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 记录登录日志
     *
     * @param accountLoginLog
     */
    @Override
    public void saveAccountLoginlog(AccountLoginLog accountLoginLog) {
        accountLoginLogMapper.insertSelective(accountLoginLog);
    }

    /**
     * 查询所有账户并进行模糊查询
     *
     * @param requestParam
     * @return
     */
    @Override
    public List<Account> findAllAccountWithRolesByQueryParam(Map<String, Object> requestParam) {
        return accountMapper.findAllWithRolesByQueryParam(requestParam);
    }

    /**
     * 新增账户
     *
     * @param account
     * @param rolesIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAccount(Account account, Integer[] rolesIds) {
        account.setCreateTime(new Date());

        //账号默认密码为手机号码的后6位
        String password;
        if(account.getAccountMobile().length() <= 6) {
            password = account.getAccountMobile();
        } else {
            password = account.getAccountMobile().substring(6);
        }
        //对密码进行MD5加密
        password = DigestUtils.md5Hex(password);

        account.setAccountPassword(password);

        //账号默认状态
        account.setAccountState(Account.STATE_NORMAL);
        accountMapper.insertSelective(account);

        //添加账号和角色的对应关系表
        for(Integer roleId : rolesIds) {
            AccountRolesKey accountRolesKey = new AccountRolesKey();
            accountRolesKey.setAccountId(account.getId());
            accountRolesKey.setRolesId(roleId);

            accountRolesMapper.insert(accountRolesKey);
        }
    }

    /**
     * 修改账号
     *
     * @param account
     * @param rolesIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateAccount(Account account, Integer[] rolesIds) {
        //添加账号的修改时间
        account.setUpdateTime(new Date());
        account.setAccountState(Account.STATE_DISABLE);
        accountMapper.updateByPrimaryKeySelective(account);

        //删除原有的账号-角色关系
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andAccountIdEqualTo(account.getId());
        accountRolesMapper.deleteByExample(accountRolesExample);

        //新增账号-角色关系
        if(rolesIds != null) {
            for (Integer rolesId : rolesIds) {
                AccountRolesKey accountRolesKey = new AccountRolesKey();
                accountRolesKey.setRolesId(rolesId);
                accountRolesKey.setAccountId(account.getId());
                accountRolesMapper.insertSelective(accountRolesKey);
            }
        }

        logger.info("修改账号 {}",account);
    }

    /**
     * 根据id查找账户
     *
     * @param id
     * @return
     */
    @Override
    public Account findById(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }
}
