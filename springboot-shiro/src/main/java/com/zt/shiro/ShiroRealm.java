package com.zt.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangtian
 * @date 2018/8/25
 */

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        roleSet.add("manager");
        simpleAuthorizationInfo.setRoles(roleSet);

        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("user:create");
        permissionSet.add("user:query");

        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();

        if(!"rose".equals(userName)) {
            throw new UnknownAccountException("账号或密码错误");
        }
        return new SimpleAuthenticationInfo(userName,"123123",getName());
    }
}
