package com.zt.erp.shiro;

import com.zt.erp.entity.Employee;
import com.zt.erp.entity.EmployeeLoginLog;
import com.zt.erp.entity.Permission;
import com.zt.erp.entity.Role;
import com.zt.erp.service.LoginService;
import com.zt.erp.service.RoleEmployeeService;
import com.zt.erp.service.RolePermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author zhangtian
 * @date 2018/7/30
 */

public class MyRealm  extends AuthorizingRealm {
    Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private RoleEmployeeService roleEmployeeService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 权限、角色授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录对象
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();

        //获取当前登录对象拥有的角色
        List<Role> roleList = roleEmployeeService.findRoleListByEmployeeId(employee.getId());

        //获取当前登录对象拥有的权限
        List<Permission> permissionList = new ArrayList<>();
        for(Role role : roleList){
            List<Permission> permissions = rolePermissionService.findPermissionByRolesId(role.getId());
           permissionList.addAll(permissions);
        }

        Set<String> roleCodeSet = new HashSet<>();
        for(Role role : roleList){
            roleCodeSet.add(role.getRoleCode());
        }

        Set<String> permissionCodeSet = new HashSet<>();
        for(Permission per : permissionList){
            permissionCodeSet.add(per.getPermissionCode());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //获取当前登录的角色(code)
        simpleAuthorizationInfo.setRoles(roleCodeSet);
        //获取当前登录的权限(code)
        simpleAuthorizationInfo.setStringPermissions(permissionCodeSet);

        return simpleAuthorizationInfo;
    }

    /**
     * 登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;

        String employeeTel = usernamePasswordToken.getUsername();
        Employee employee = roleEmployeeService.findEmployeeByTel(employeeTel);

        if(employee == null){
            throw new UnknownAccountException();
        }else{
            if(employee.getState().equals(Employee.EMPLOYEE_STATE_UNNOMAL)){
                throw new LockedAccountException();
            }else{
                String loginIp = usernamePasswordToken.getHost();
                // 记录登录日志
                EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
                employeeLoginLog.setLoginIp(loginIp);
                employeeLoginLog.setLoginTime(new Date());
                employeeLoginLog.setEmployeeId(employee.getId());
                loginService.saveLoginLog(employeeLoginLog);

                logger.info("{}-{} 在 {} 登录了系统", employee.getEmployeeName(),employee.getEmployeeTel(),new Date());

                return new SimpleAuthenticationInfo(employee,employee.getPassword(),getName());
            }
        }
    }
}
