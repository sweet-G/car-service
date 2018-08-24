package com.zt.controller;

import com.github.pagehelper.PageHelper;
import com.zt.dao.StudentDao;
import com.zt.entity.Student;
import com.zt.entity.StudentExample;
import com.zt.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhangtian
 * @date 2018/8/22
 */
@Controller
public class HomeController {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("message","<em style='color:red'>你好,SpringBoot!</em>");
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

    @GetMapping("/new")
    public String new1(Model model){

        model.addAttribute("name","rose");

        Map<String,String> map = new HashMap<>();
        map.put("id","1000");
        map.put("name","tom");
        model.addAttribute("map",map);

        model.addAttribute("time",System.currentTimeMillis());
        model.addAttribute("money",12345678963546L);
        return "new";
    }

    @GetMapping("/stu")
    @ResponseBody
    public String count(){
        int count = studentDao.count();
        return "count: " + count;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Student> all(){
        PageHelper.startPage(1,3);
        return studentMapper.selectByExample(new StudentExample());
    }
}
