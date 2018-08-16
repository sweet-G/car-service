package com.zt.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/16
 */

public class QuartzTaskTestCase {

    @Test
    public void testSimple() throws SchedulerException, IOException {
        //创建Job指定的执行任务
        JobDetail jobDetail = JobBuilder.newJob(QuartzTaskTimer.class).build();

        //创建trigger并定义何时触发
        SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
        //设置时间间隔
        ssb.withIntervalInSeconds(3);
        //永不间断
        ssb.repeatForever();

        Trigger simpleTrigger = TriggerBuilder.newTrigger().withSchedule(ssb).build();

        //创建schedluer并调度任务
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,simpleTrigger);
        scheduler.start();

        System.in.read();
    }

    @Test
    public void testCron() throws SchedulerException, IOException {
        //创建Job指定执行任务
        JobDetail jobDetail = JobBuilder.newJob(QuartzTaskTimer.class).build();

        //创建trigger并定义何时触发
        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ? *");
        Trigger cornTrigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

        //创建schedluer并调度任务
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,cornTrigger);
        scheduler.start();

        System.in.read();
    }
}
