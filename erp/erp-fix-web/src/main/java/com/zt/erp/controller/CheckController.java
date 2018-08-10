package com.zt.erp.controller;

import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Employee;
import com.zt.erp.entity.FixOrder;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.FixOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/10
 */
@Controller
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private FixOrderService fixOrderService;

    @GetMapping("/list")
    public String list(Model model){
        List<FixOrder> fixOrderList = fixOrderService.findCheckOrderListWithParts();

        model.addAttribute("fixOrderList",fixOrderList);
        return "check/list";
    }

    @GetMapping("/{id:\\d+}/receive")
    @ResponseBody
    public ResponseBean checkReceiveTask(@PathVariable Integer id) throws ServiceException {
        try {
            //获得当前员工
            Subject subject = SecurityUtils.getSubject();
            Employee employee = (Employee) subject.getPrincipal();
            fixOrderService.checkReceiveTask(id, employee);

            return ResponseBean.success();
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

    }

    @GetMapping("/{id:\\d+}/detail")
    public String checkDetail(@PathVariable Integer id, Model model){
        FixOrder fixOrder = fixOrderService.findCheckOrderById(id);

        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("fixOrder", fixOrder);
        return "check/detail";
    }

    @GetMapping("/{id:\\d+}/done")
    public String CheckDone(@PathVariable Integer id){
        fixOrderService.CheckTaskDone(id);
        return "redirect:/check/list";
    }
}
