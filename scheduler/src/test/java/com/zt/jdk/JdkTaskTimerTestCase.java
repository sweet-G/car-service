package com.zt.jdk;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

/**
 * @author zhangtian
 * @date 2018/8/16
 */

public class JdkTaskTimerTestCase {

    @Test
    public void testJdkRun() throws IOException {
        Timer timer = new Timer();
        //获取当前时间点并在2秒后执行任务
        //timer.schedule(new JdkTaskTimer(),new Date(System.currentTimeMillis() + 2000L));

        //任务延迟一定时间后执行
        //timer.schedule(new JdkTaskTimer(),2000);

        //首次延迟2秒，以后每次1秒执行
        // timer.schedule(new JdkTaskTimer(), 2000, 1000);

        // 在某个时间点第一次执行，然后每一秒钟执行一次
        // timer.schedule(new MyTimerTask(), new Date(System.currentTimeMillis() + 3000L), 1000);

        // fixRate是相对于上一次的任务的开始时间计时时间间隔，如果第二次任务开始执行时第一次任务还未完成，等待第一次任务完成之后在执行第二次任务
        timer.scheduleAtFixedRate(new JdkTaskTimer(), new Date(System.currentTimeMillis() + 2000L), 1500);
        System.in.read();
    }
}
