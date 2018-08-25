package com.zt.controller;

import com.zt.entity.Student;
import com.zt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangtian
 * @date 2018/8/24
 */
@Controller
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id:\\d+}")
    @ResponseBody
    public Student findById(@PathVariable Integer id){
        return studentService.findById(id);
    }
}
