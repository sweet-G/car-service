package com.zt.dubbo.web;

import com.zt.dubbo.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/11
 */

public class App {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        //UserService接收服务的地址名称
        UserService userService = (UserService) context.getBean("UserService");

        String name = userService.findById(1000);
        System.out.println("name-->" + name);

        System.in.read();
    }
}
