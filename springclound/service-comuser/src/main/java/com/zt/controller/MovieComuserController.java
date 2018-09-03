package com.zt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@RestController
public class MovieComuserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/get/movie/{id:\\d+}")
    public String getMovieName(@PathVariable Integer id){
        ServiceInstance instance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
        //String url = "http://"+instance.getHost()+":"+instance.getPort()+"/movie/"+id;
        String url = instance.getUri()+"/movie/"+id;
        return restTemplate.getForObject(url,String.class);
        //return name;
    }
}
