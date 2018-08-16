package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.SttleOrder;
import com.zt.erp.service.SttleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/15
 */
@Controller
@RequestMapping("/settle")
public class SettleController {

    @Autowired
    private SttleOrderService sttleOrderService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) Integer orderId,
                       @RequestParam(required = false) String tel,
                       Model model){
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("orderId",orderId);
        maps.put("tel",tel);

        PageInfo<SttleOrder> page = sttleOrderService.findSttlePageByMap(pageNo,maps);
        model.addAttribute("page",page);
        return "settle/list";
    }
}
