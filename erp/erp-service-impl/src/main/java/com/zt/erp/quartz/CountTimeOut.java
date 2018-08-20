package com.zt.erp.quartz;

import com.zt.erp.service.FixOrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * @author zhangtian
 * @date 2018/8/18
 */

public class CountTimeOut implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            FixOrderService fixOrderService = applicationContext.getBean(FixOrderService.class);

            fixOrderService.addFixTimeOut(jobName);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
