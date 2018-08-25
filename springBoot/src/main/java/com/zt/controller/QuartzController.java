package com.zt.controller;

import com.zt.job.QuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Controller
public class QuartzController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @GetMapping("/quartz")
    @ResponseBody
    public String Quartz(){
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).build();

        CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(schedule).build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
