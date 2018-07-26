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
     * 登录
     * @param employeeTel 手机号
     * @param password    密码
     * @param loginIp     登录IP
     * @return employee对象
     */
    @Override
    public Employee login(String employeeTel, String password, String loginIp) throws ServiceException {

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(employeeTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

        Employee employee = null;
        if(employeeList != null && !employeeList.isEmpty()){
            employee = employeeList.get(0);
            //判断密码是否正确
            if(employee.getPassword().equals(DigestUtils.md5Hex(password))){
                if(employee.getState().equals(Employee.EMPLOYEE_STATE_NOMAL)){

                    EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
                    employeeLoginLog.setLoginIp(loginIp);
                    employeeLoginLog.setLoginTime(new Date());
                    employeeLoginLog.setEmployeeId(employee.getId());
                    employeeLoginLogMapper.insertSelective(employeeLoginLog);

                    logger.info("{}-{}在{}",employee.getEmployeeName(),employee.getEmployeeTel(),new Date());
                }else{
                    throw new ServiceException("状态异常，请联系管理员");
                }
            }else{
                throw new ServiceException("用户名或密码错误");
            }
        }else{
            throw new ServiceException("用户名或密码错误");
        }
        return null;
    }
}
