package com.zt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, DataSourceTransactionManager dataSourceTransactionManager) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //配置文件的位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        //数据库连接池
        schedulerFactoryBean.setDataSource(dataSource);
        //事务管理器
        schedulerFactoryBean.setTransactionManager(dataSourceTransactionManager);
        //覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //将Spring容器注入到job中
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        return schedulerFactoryBean;
    }
}
