package com.zt.tms.service;

import com.zt.tms.entity.Account;
import com.zt.tms.entity.AccountLoginLog;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/30
 */
public interface AccountService {
    /**
     * 根据姓名查找对象
     * @param username
     * @return
     */
    Account findAccountMoblie(String username);

    /**
     * 记录登录日志
     * @param accountLoginLog
     */
    void saveAccountLoginlog(AccountLoginLog accountLoginLog);

    /**
     * 查询所有账户并进行模糊查询
     * @param requestParam
     * @return
     */

    /**
     * 新增账户
     * @param account
     * @param rolesIds
     */
    void saveAccount(Account account, Integer[] rolesIds);

    /**
     * 修改账号
     * @param account
     * @param rolesIds
     */
    void updateAccount(Account account, Integer[] rolesIds);

    /**
     * 根据id查找账户
     * @param id
     * @return
     */
    Account findById(Integer id);

     List<Account> findAllAccountWithRolesByQueryParam(Map<String,Object> requestParam);
}
