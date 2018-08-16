package com.zt.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangtian
 * @date 2018/8/16
 */
@Component
public class SpringAnnotation {

    @Scheduled(fixedRate = 1000)
    @Async
    public void run(){
        System.out.println(Thread.currentThread().getName());
        System.out.println("spring task annotation running...");
        try {
            System.out.println("this is a rate job");
            Thread.sleep(2000);
            if(1 == 1) {
                throw new RuntimeException("出异常了...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end.............");
    }
}
