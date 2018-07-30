package com.zt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author zhangtian
 * @date 2018/7/30
 */

public class QuickStart {

    private static Logger logger = LoggerFactory.getLogger(QuickStart.class);

    public static void main(String[] args) {
        //1. IniSecurityManagerFactory读取配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager =  factory.getInstance();

        //2. 设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //3. 通过getSubject()获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //4. 判断当前subject对象是否通过认证
        if(!subject.isAuthenticated()){
            //如果没有通过认证，则用账户密码登录
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin","123");

            //5. 通过login登录
            try {
                subject.login(usernamePasswordToken);
                 //获得当前登录的用户名称
                logger.info("{} login success", subject.getPrincipal());
                //判断当前主体是否拥有某个角色
                logger.info("hasRole: {},{}","admin",subject.hasRole("admin"));
                //判断当前主体是否对多个角色的拥有权
                boolean[] results = subject.hasRoles(Arrays.asList("admin","superAdmin"));
                for(Boolean result : results){
                    logger.info("result: {}",result);
                }
                //判断当前主体对所有角色的拥有权
                logger.info("all: {}", subject.hasAllRoles(Arrays.asList("admin","tom")));
                //判断当前主体是否拥有某角色,如果没有抛异常
                subject.checkRole("admin");

                //判断当前登录对象是否拥有该权限
                logger.info("employee:add -->{}",subject.isPermitted("employee:add"));
                //判断当前登录对象是否拥有多个权限
                logger.info("employee:all -->{}",subject.isPermittedAll("employee:query","employee:delete"));
                //判断当前登录对象是否拥有某权限，如果没有抛异常
                subject.checkPermission("employee:add");

                //Session存值取值,大多数情况下可代替HttpSession
                Session session = subject.getSession();
                //存值
                session.setAttribute("username",subject.getPrincipal());
                //取值
                logger.info("session: {}",session.getAttribute("username"));

                //安全退出
                subject.logout();

            } catch (UnknownAccountException e) {
                logger.info("用户名错误");
            } catch (IncorrectCredentialsException e){
                logger.info("密码错误");
            } catch (LockedAccountException e){
                logger.info("账户冻结");
            } catch (AuthenticationException e){
                logger.info("认证失败");
            }
        }

    }


}
