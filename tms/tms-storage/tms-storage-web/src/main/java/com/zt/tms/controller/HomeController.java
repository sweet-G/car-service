package com.zt.tms.controller;

import com.zt.tms.dto.ResponseBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangtian
 * @date 2018/8/30
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String login(){
        Subject subject = SecurityUtils.getSubject();

        //判断当前登录账户有是否已认证，如果有，
        if(subject.isAuthenticated()){
            subject.logout();
        }

        if(subject.isRemembered()){
            return "redirect:/home";
        }

        return  "index";
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseBean login(String accountMobile,
                        String password,
                        String rememberMe,
                        HttpServletRequest request){

        Subject subject = SecurityUtils.getSubject();

        String loginIp = request.getRemoteAddr();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(accountMobile,DigestUtils.md5Hex(password),rememberMe != null,loginIp);

        try {
            subject.login(usernamePasswordToken);

        if(subject.hasRole("ticket:manage")){
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = "/home";
            if(savedRequest != null){
                url = savedRequest.getRequestUrl();
            }
            return ResponseBean.success(url);
        }else{
            return ResponseBean.error("没有权限访问");
        }

        } catch (UnknownAccountException | IncorrectCredentialsException ex) {
            ex.printStackTrace();
            return ResponseBean.error("账号或密码错误");
        } catch (LockedAccountException ex) {
            ex.printStackTrace();
            return ResponseBean.error("账号被锁定");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return ResponseBean.error("账号或密码错误");
        }
    }

    /**
     * 登录后的首页
     * @return
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }


    @GetMapping("/401")
    public String unauthorizedUrl() {
        return "error/401";
    }
}
