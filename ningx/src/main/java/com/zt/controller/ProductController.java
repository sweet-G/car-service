package com.zt.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/28
 */
@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @GetMapping("/{id:\\d+}")
    public Map<String, Object> product(@PathVariable Integer id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",1001);
        map.put("productName","lenvov");
        map.put("productPrice",5462.56);

        return map;
    }
}
