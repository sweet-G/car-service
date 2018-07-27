package com.zt.erp.controller;

import com.zt.erp.entity.Employee;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhangtian
 * @date 2018/7/26
 */

@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String login(@CookieValue(required = false) String employeeTel, Model model){
            model.addAttribute("employeeTel",employeeTel);
        return "index";
    }

    @PostMapping("/")
    public String login(String employeeTel,
                        String password,
                        String remember,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes){
        //获取登录ip
        String loginIp = request.getRemoteAddr();

        try {
            Employee employee = loginService.login(employeeTel,password,loginIp);

            HttpSession session = request.getSession();
            session.setAttribute("employee",employee);

            if(StringUtils.isNotEmpty(remember)){
                Cookie cookie = new Cookie("employeeTel",employeeTel);
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24);
                cookie.setHttpOnly(true);

                response.addCookie(cookie);

            }else {
                Cookie[] cookies = request.getCookies();
                if(cookies != null){
                    for (Cookie cookie : cookies) {
                        if("employeeTel".equals(cookie.getName())){
                            cookie.setDomain("localhost");
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            break;
                        }
                    }
                }

            }
            return "redirect:/home";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String  logout(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session != null){
            session.removeAttribute("employee");
            return "redirect:/";
        }
        return "redirect:/";
    }


}
