package com.zt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author zhangtian
 * @date 2018/8/27
 */
@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String index(HttpServletRequest request, HttpSession session){

        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headers = headerNames.nextElement();
            String value = request.getHeader(headers);
            stringBuilder.append("headers: "+ headers+" value: "+ value).append("<br/>");
        }

        stringBuilder.append("---------------------------------------------------------------<br/>");
        String realIp = request.getHeader("x-real-ip");
        String ip = request.getRemoteUser();
        String sessionId = session.getId();

        stringBuilder.append("realIp: "+ realIp + "ip: "+ ip + "sessionIp: " + sessionId);

        return stringBuilder.toString();
    }

}
