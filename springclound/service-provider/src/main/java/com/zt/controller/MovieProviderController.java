package com.zt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@RestController
public class MovieProviderController {

    @GetMapping("/movie/{id:\\d+}")
    public String movieName(@PathVariable Integer id){
        System.out.println("id:-------->" + id);
        return "大话西游";
    }
}
