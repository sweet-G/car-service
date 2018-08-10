package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Employee;
import com.zt.erp.entity.FixOrder;
import com.zt.erp.entity.FixOrderParts;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.FixOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private FixOrderService fixOrderService;

    @GetMapping("/list")
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) Integer orderId,

                       Model model){
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("orderId",orderId);

        PageInfo<FixOrder> page = fixOrderService.findPageByMap(pageNo,maps);

        List<FixOrder> fixOrderList = fixOrderService.findFixOrderListWithParts();

        model.addAttribute("fixOrderList",fixOrderList);
        model.addAttribute("page",page);
        return "fix/list";
    }

    @GetMapping("/{id:\\d+}/receive")
    @ResponseBody
    public ResponseBean receiveTask(@PathVariable Integer id) throws ServiceException{
        try {
            //获得当前员工
            Subject subject = SecurityUtils.getSubject();
            Employee employee = (Employee) subject.getPrincipal();
            fixOrderService.taskReceive(id, employee);

            return ResponseBean.success();
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

    }

    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable Integer id, Model model){
        FixOrder fixOrder = fixOrderService.findFixOrderById(id);

        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("fixOrder", fixOrder);
        return "fix/detail";
    }

    @GetMapping("/{id:\\d+}/done")
    public String done(@PathVariable Integer id){
        fixOrderService.taskDone(id);
        return "redirect:/fix/list";
    }

}
