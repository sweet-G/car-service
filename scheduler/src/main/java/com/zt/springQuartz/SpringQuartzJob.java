package com.zt.springQuartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zhangtian
 * @date 2018/8/16
 */

public class SpringQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        System.out.println("jobDataMap" + jobDataMap.get("message"));
        System.out.println("spring quartz running ...");
    }
}
