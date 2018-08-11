package com.zt.dubbo.serviceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/11
 */

public class App {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-provider.xml");
        ((ClassPathXmlApplicationContext) context).start();

        System.out.println("容器启动");
        System.in.read();
    }
}
