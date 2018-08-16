package com.zt.springQuartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/8/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-quartz.xml")
public class MyQuartzJobTestCase {

    @Test
    public void run() throws IOException {
        System.out.println("容器启动...");
        System.in.read();
    }

}
