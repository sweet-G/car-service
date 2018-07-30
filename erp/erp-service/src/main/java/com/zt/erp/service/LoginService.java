package com.zt.erp.service;

import com.zt.erp.entity.Employee;
import com.zt.erp.entity.EmployeeLoginLog;

/**
 * @author zhangtian
 * @date 2018/7/26
 */

public interface LoginService {
    /**
     * 根据电话密码查询员工
     * @param employeeTel
     * @param password
     * @return
     */
    Employee findByEmployeeTel(String employeeTel, String password);

    /**
     * 记录登录者的ip
     * @param loginIp
     * @param id
     */
    void login(String loginIp, Integer id);

    /**
     * 保存日志
     * @param employeeLoginLog
     */
    void saveLoginLog(EmployeeLoginLog employeeLoginLog);
}
