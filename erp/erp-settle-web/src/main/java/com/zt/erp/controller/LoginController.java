package com.zt.erp.controller;

import com.zt.erp.entity.Employee;
import com.zt.erp.entity.Role;
import com.zt.erp.service.LoginService;
import com.zt.erp.service.RoleEmployeeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/26
 */

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleEmployeeService roleEmployeeService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String login(){
        Subject subject = SecurityUtils.getSubject();

        /*//如果账户通过认证，则退出登录
        if(subject.isAuthenticated()) {
            subject.logout();
        }*/

        //如果记住我，则返回首页
        if(subject.isRemembered()) {
            return "home";
        }

        return "index";
    }

    @PostMapping("/")
    public String login(String employeeTel,
                        String password,
                        String remember,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes){
        //获得Subject主体对象
        Subject subject = SecurityUtils.getSubject();
        //获得登录的Ip
        String loginIp = request.getRemoteAddr();
        //通过employeeTel、password封装UsernamePasswordToken对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(employeeTel,DigestUtils.md5Hex(password),remember != null,loginIp);

        try {
            subject.login(usernamePasswordToken);

            if(subject.hasRole("settle:service")) {

                Employee employee = roleEmployeeService.findEmployeeByTel(employeeTel);
                Session session = subject.getSession();
                session.setAttribute("employee",employee);

                //跳转到登录前的请求页面
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/home";
                if(savedRequest != null){
                    url = savedRequest.getRequestUrl();
                }
                return "redirect:" + url;
            }else{
                redirectAttributes.addFlashAttribute("message", "您没有访问该系统的权限");
            }
        } catch (UnknownAccountException |IncorrectCredentialsException e) {
            redirectAttributes.addFlashAttribute("message", "用户名或者密码错误");
        } catch (LockedAccountException e) {
            redirectAttributes.addFlashAttribute("message", "账户冻结，不能登录");
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("message", "登录失败");
        }
        return "redirect:/";

        /*Employee employee = null;
        try {
            employee = loginService.findByEmployeeTel(employeeTel, password);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/";
        }

        session.setAttribute("employee",employee);
        //获取登录ip
        String loginIp = request.getRemoteAddr();

        loginService.login(loginIp,employee.getId());

            if(StringUtils.isNotEmpty(remember)){
                Cookie cookie = new Cookie("employeeTel",employee.getEmployeeTel());
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
*/
    }

    /*@GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectAttributes.addFlashAttribute("message", "已退出，请重新登录");
        return "redirect:/";

        *//*session.removeAttribute("employee");
        return "redirect:/";*//*
    }*/

    @GetMapping("/profile/{id:\\d+}")
    public String profile(@PathVariable Integer id, Model model){
        Employee employee = roleEmployeeService.findEmployeeById(id);
        List<Role> roleList = roleEmployeeService.findRoleListByEmployeeId(id);

        model.addAttribute("roleList",roleList);
        model.addAttribute("employee",employee);
        return "profile";
    }

    @PostMapping("/profile/{id:\\d+}")
    public String profile(){
        return "profile";
    }

    @GetMapping("/profile/{id:\\d+}/edit")
    public String editProfile(@PathVariable Integer id, Model model){
        Employee employee = roleEmployeeService.findEmployeeById(id);
        List<Role> roleList = roleEmployeeService.findRoleListByEmployeeId(id);

        model.addAttribute("roleList",roleList);
        model.addAttribute("employee",employee);

        return "profileEdit";
    }

    @PostMapping("/profile/{id:\\d+}/edit")
    public String editProfile(Employee employee,RedirectAttributes redirectAttributes){
        roleEmployeeService.editProfileEmployee(employee);

        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/home";
    }



    @GetMapping("/401")
    public String unauthorizedUrl(){
        return "error/401";
    }

}
