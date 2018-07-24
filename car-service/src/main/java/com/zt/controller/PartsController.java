package com.zt.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zt.entity.Parts;
import com.zt.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangtian
 * @date 2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    @GetMapping
    public String list(@RequestParam(name = "p",defaultValue = "1") Integer pageNo, Model model){

        PageInfo<Parts> page = partsService.findPage(pageNo);
        model.addAttribute("page",page);
        return "parts/list";
    }


    @GetMapping("/{id:\\d+}")
    public String findById(@PathVariable Integer id, Model model){
        Parts parts = partsService.findById(id);
        model.addAttribute("parts",parts);
        return "parts/detail";
    }
}
