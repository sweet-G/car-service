package com.zt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangtian
 * @date 2018/7/30
 */

public class MyRealmQuickStart {

    private static Logger logger = LoggerFactory.getLogger(QuickStart.class);

    public static void main(String[] args) {
        //1. IniSecurityManagerFactory读取配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:my-realm.ini");
        SecurityManager securityManager = factory.getInstance();

        //2. 设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //3. 通过getSubject()获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //4. 判断当前subject对象是否通过认证
        if (!subject.isAuthenticated()) {
            //如果没有通过认证，则用账户密码登录
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin", "123");

            //5. 通过login登录
            try {
                subject.login(usernamePasswordToken);
                //获得当前登录的用户名称
                logger.info("{} login success", subject.getPrincipal());
            } catch (UnknownAccountException e) {
                logger.info("用户名错误");
            } catch (IncorrectCredentialsException e) {
                logger.info("密码错误");
            } catch (LockedAccountException e) {
                logger.info("账户冻结");
            } catch (AuthenticationException e) {
                logger.info("认证失败");
            }
        }
    }
}