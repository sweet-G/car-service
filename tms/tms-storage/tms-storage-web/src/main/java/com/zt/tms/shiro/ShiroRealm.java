package com.zt.tms.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zt.tms.entity.Account;
import com.zt.tms.entity.AccountLoginLog;
import com.zt.tms.entity.Permission;
import com.zt.tms.entity.Roles;
import com.zt.tms.service.AccountService;
import com.zt.tms.service.RolePermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zhangtian
 * @date 2018/8/30
 */

public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Reference(version = "1.0")
    private AccountService accountService;

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的对象
        Account account = (Account) principalCollection.getPrimaryPrincipal();
        //获取当前登录对象拥有的角色
        List<Roles> rolesList = rolePermissionService.findRolesByAccountId(account.getId());
        //获取当前登录对象拥有的权限
        List<Permission> permissionList = new ArrayList<>();
        for(Roles roles : rolesList) {
            List<Permission> rolesPermissionList = rolePermissionService.findAllPermissionByRolesId(roles.getId());
            permissionList.addAll(rolesPermissionList);
        }

        Set<String> rolesNameSet = new HashSet<>();
        for(Roles roles : rolesList) {
            rolesNameSet.add(roles.getRolesCode());
        }

        Set<String> permissionNameSet = new HashSet<>();
        for(Permission permission : permissionList) {
            permissionNameSet.add(permission.getPermissionCode());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //当前用户拥有的角色（code）
        simpleAuthorizationInfo.setRoles(rolesNameSet);
        //当前用户拥有的权限(code)
        simpleAuthorizationInfo.setStringPermissions(permissionNameSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String username = usernamePasswordToken.getUsername();
        if(username != null){
            Account account = accountService.findAccountMoblie(username);
            if(account == null){
                throw  new UnknownAccountException("账户不存在" + username);
            }else{
                if(Account.STATE_NORMAL.equals(account.getAccountState())){
                    logger.info("{}登录成功{}",account,usernamePasswordToken.getHost());

                    AccountLoginLog accountLoginLog = new AccountLoginLog();
                    accountLoginLog.setAccountId(account.getId());
                    accountLoginLog.setLoginIp(usernamePasswordToken.getHost());
                    accountLoginLog.setLoginTime(new Date());
                    accountService.saveAccountLoginlog(accountLoginLog);

                    return  new SimpleAuthenticationInfo(account,account.getAccountPassword(),getName());
                }else{
                    throw new LockedAccountException("账号被禁用或锁定:" + account.getAccountState());

                }
            }
        }
        return null;
    }
}
