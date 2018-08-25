package com.zt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zt.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangtian
 * @date 2018/8/25
 */
@RestController
public class UserController {
    @Reference(version = "1.0")
    private UserService userService;

    @GetMapping("/{id:\\d+}")
    public String findById(@PathVariable Integer id){
        return userService.findById(id);
    }
}
