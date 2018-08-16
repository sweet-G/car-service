package com.zt.springQuartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-quartz-db.xml")
public class SpringQuartzJobTestCase {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private int taskId = 1000;
    @Test
    public void run() throws IOException {
        System.out.println("容器启动...");
        System.in.read();
    }

    @Test
    public void rundb(){

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("message","running...");

            JobDetail jobDetail = JobBuilder.newJob(SpringQuartzJob.class)
                    .withIdentity("task" + taskId,"springQuartzJob")
                    .setJobData(jobDataMap)
                    .build();

            String cronExpression = "0/3 * 20 16 8 ? *";

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void del() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.deleteJob(new JobKey("task" + taskId, "springQuartzJob"));

    }

}
