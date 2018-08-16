package com.zt.jdk;

import java.util.TimerTask;

/**
 * @author zhangtian
 * @date 2018/8/16
 */

public class JdkTaskTimer extends TimerTask {
    @Override
    public void run() {
        System.out.println("jdk task running...");
    }
}
