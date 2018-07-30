package com.zt.erp.service.Impl;

import com.zt.erp.entity.Employee;
import com.zt.erp.entity.EmployeeExample;
import com.zt.erp.entity.EmployeeLoginLog;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.EmployeeLoginLogMapper;
import com.zt.erp.mapper.EmployeeMapper;
import com.zt.erp.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/26
 */
@Service
public class LoginServiceImpl implements LoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;

    /**
     * 根据电话密码查询员工
     *
     * @param employeeTel
     * @param password
     * @return
     */
    @Override
    public Employee findByEmployeeTel(String employeeTel, String password) throws ServiceException{
        password = DigestUtils.md5Hex(password);

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(employeeTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

        if(employeeList == null){
            throw new ServiceException("手机号或密码错误!");
        }
        if(employeeList.size() == 0){
            throw new ServiceException("手机号或密码错误!");
        }
        Employee employee = employeeList.get(0);
        if(!employee.getPassword().equals(password)){
            throw new ServiceException("手机号或密码错误!");
        }
        logger.debug("{}--{}, 登录系统,时间{}", employee.getEmployeeName(), employee.getEmployeeTel(), new Date());
        return employee;
    }

    /**
     * 记录登录者的ip
     *
     * @param loginIp
     * @param id
     */
    @Override
    public void login(String loginIp, Integer id) {
        EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();

        employeeLoginLog.setLoginIp(loginIp);
        employeeLoginLog.setEmployeeId(id);
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }

    /**
     * 保存日志
     *
     * @param employeeLoginLog
     */
    @Override
    public void saveLoginLog(EmployeeLoginLog employeeLoginLog) {
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }


}
