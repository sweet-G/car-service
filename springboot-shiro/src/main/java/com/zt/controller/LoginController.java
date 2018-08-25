package com.zt.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author zhangtian
 * @date 2018/8/25
 */
@Controller
public class LoginController {

    @GetMapping("/")
    public String login(){
        return "login";
    }

    @PostMapping("/")
    public String login(String userName, String password, RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password);

        try {
            subject.login(usernamePasswordToken);
            return "redirect:/home";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/";
        }

    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/add")
    public String add(){
        return "add";
    }
}
