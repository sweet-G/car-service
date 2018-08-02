package com.zt.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangtian
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/list")
    public String listOder() {

        return "order/list";
    }

    @GetMapping("/new")
    public String newOrder(Model model) {
        return "order/new";
    }

    @PostMapping("/new")
    public String newOrder(){
        return "";
    }
}
