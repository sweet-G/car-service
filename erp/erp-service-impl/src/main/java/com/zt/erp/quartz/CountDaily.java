package com.zt.erp.quartz;

import com.zt.erp.service.Impl.OrderServiceImpl;
import com.zt.erp.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * @author zhangtian
 * @date 2018/8/17
 */

public class CountDaily implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            OrderService orderService = applicationContext.getBean(OrderService.class);
            orderService.orderCountDaily();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
