package com.zt.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangtian
 * @date 2018/8/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis-cluster.xml")
public class RedisClusterTest {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void testSet() throws IOException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(5);

        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        hostAndPorts.add(new HostAndPort("192.168.1.142",6000));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6001));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6002));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6003));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6004));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,config);
        jedisCluster.set("name","lili");

        jedisCluster.close();

    }

    @Test
    public void testGet() throws IOException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(5);

        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        hostAndPorts.add(new HostAndPort("192.168.1.142",6000));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6001));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6002));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6003));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6004));
        hostAndPorts.add(new HostAndPort("192.168.1.142",6005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,config);
        String name = jedisCluster.get("name");

        System.out.println(name);
        jedisCluster.close();
    }

    @Test
    public void testRedis() throws IOException {
        jedisCluster.set("address","杭州");
        jedisCluster.close();
    }

}
