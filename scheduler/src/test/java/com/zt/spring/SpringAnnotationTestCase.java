package com.zt.spring;

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
@ContextConfiguration(locations = "classpath:springAnnotationTask.xml")
public class SpringAnnotationTestCase {

    @Test
    public void run() throws IOException {
        System.out.println("spring start...");
        System.in.read();
    }
}
