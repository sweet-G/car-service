package com.zt.client;

import com.zt.entity.Movie;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@FeignClient("MOVIE-SERVICE-PROVIDER")
public interface MovieServiceClient {

    @GetMapping("/movie/{id}")
    String movieName(@PathVariable Integer id);

    @PostMapping("/movie/new")
    String save(@RequestParam(name = "movieName") String movieName);

    @PostMapping("/movie/save")
    String movie(@RequestBody Movie movie);
}
