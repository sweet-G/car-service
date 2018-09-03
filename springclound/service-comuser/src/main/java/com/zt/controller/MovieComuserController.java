package com.zt.controller;

import com.zt.client.MovieServiceClient;
import com.zt.entity.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

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

    private MovieServiceClient movieServiceClient;

    /*@Autowired
    private LoadBalancerClient loadBalancerClient;
*/
    @GetMapping("/get/movie/{id:\\d+}")
    public String getMovieName(@PathVariable Integer id){
        String url = "http://MOVIE-SERVICE-PROVIDER/movie/"+id;
        return restTemplate.getForObject(url,String.class);
    }

    @PostMapping("/buy/movie/new")
    public String newMovie(String movieName) {
        return movieServiceClient.save(movieName);
    }

    @PostMapping("/buy/movie/save")
    public String saveMovie(String movieName) {
        Movie movie = new Movie();
        movie.setMovieName("007");
        return movieServiceClient.movie(movie);
    }
    /*@GetMapping("/get/movie/{id:\\d+}")

    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/get/movie/{id:\\d+}")

    public String getMovieName(@PathVariable Integer id){
        ServiceInstance instance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
        //String url = "http://"+instance.getHost()+":"+instance.getPort()+"/movie/"+id;
        String url = instance.getUri()+"/movie/"+id;

        return restTemplate.getForObject(url,String.class);
        //return name;
    }*/


        //return restTemplate.getForObject(url,String.class);
        //return name;


}
