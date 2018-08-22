package com.zt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.WebParam;
import java.util.Arrays;
import java.util.List;


/**
 * @author zhangtian
 * @date 2018/8/22
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("name","果果");

        List<String>  list = Arrays.asList("rose","jack","tom");
        model.addAttribute("list",list);
        return "home";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("name","jack");
        model.addAttribute("role","admin");
        model.addAttribute("id",1000);
        return "form";
    }
}
