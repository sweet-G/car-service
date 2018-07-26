package com.zt.erp.service;

import com.zt.erp.entity.Employee;
import com.zt.erp.exception.ServiceException;

/**
 * @author zhangtian
 * @date 2018/7/26
 */

public interface LoginService {
    /**
     * 登录
     * @param employeeTel 手机号
     * @param password 密码
     * @param loginIp 登录IP
     * @return employee对象
     */
    Employee login(String employeeTel, String password, String loginIp) throws ServiceException;
}
