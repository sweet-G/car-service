package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Order;
import com.zt.erp.entity.OrderParts;
import com.zt.erp.entity.Parts;
import com.zt.erp.service.FixService;
import com.zt.erp.service.OrderService;
import com.zt.erp.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/7
 */
@Controller
@RequestMapping("/fix")
public class FixController {

    @Autowired
    private FixService fixService;
    @Autowired
    private PartsService partsService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) Integer orderId,

                       Model model){
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("orderId",orderId);

        PageInfo<Order> page = fixService.findPageByMap(pageNo,maps);
        List<Parts> partsList = partsService.findAllOrderWithParts(orderId);

        model.addAttribute("page",page);
        model.addAttribute("partsList",partsList);
        return "fix/list";
    }
}
