package com.zt.controller;

import com.zt.entity.Movie;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/movie/new")
    public String newMovie(String movieName, String year) {
        System.out.println("movieName: " + movieName);
        System.out.println("year:" + year);
        return "save success!";
    }

    @PostMapping("/movie/save")
    public String saveMovie(@RequestBody Movie movie) {
        System.out.println("movieName: " + movie.getMovieName());
        return "save success!";
    }

}
