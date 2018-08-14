package com.zt.erp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/13
 */

public class App {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ((ClassPathXmlApplicationContext) context).start();

        System.out.println("容器启动");

        System.in.read();
    }

}
