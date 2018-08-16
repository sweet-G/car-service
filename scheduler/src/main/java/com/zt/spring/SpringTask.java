package com.zt.spring;

/**
 * @author zhangtian
 * @date 2018/8/16
 */

public class SpringTask implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " spring task begin...");
        System.out.println("spring task running...");
        System.out.println("end-------------------");
    }
}
