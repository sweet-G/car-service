package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Employee;
import com.zt.erp.entity.Role;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.RoleEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/28
 */
@Controller
@RequestMapping("/manage/employee")
public class EmployeeController {

    @Autowired
    private RoleEmployeeService roleEmployeeService;

    @GetMapping
    public String home(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) String nameMobile,
                       @RequestParam(required = false) String rolesId,
                       Model model){
        Map<String, Object> maps = Maps.newLinkedHashMap();
        maps.put("nameMobile",nameMobile);
        maps.put("rolesId",rolesId);

        PageInfo<Employee> page = roleEmployeeService.findPageWithMap(pageNo,maps);
        List<Role> roleList = roleEmployeeService.findAllRoles();

        model.addAttribute("page",page);
        model.addAttribute("roleList",roleList);
        return "manage/employee/home";
    }

    @GetMapping("/new")
    public String newEmployee(Model model){
        List<Role> roleList = roleEmployeeService.findAllRoles();

        model.addAttribute("roleList",roleList);
        return "manage/employee/new";
    }

    @PostMapping("/new")
    public String newEmployee(Employee employee, Integer[] roleId,RedirectAttributes redirectAttributes){
        try {
            roleEmployeeService.saveEmployee(employee,roleId);
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/manage/employee";
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delEmployee(@PathVariable Integer id){
        try {
            roleEmployeeService.delEmployee(id);
        } catch (ServiceException e) {
           return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editEmployee(@PathVariable Integer id, Model model){

        Employee employee = roleEmployeeService.findEmployeeById(id);

        List<Role> roleList = roleEmployeeService.findAllRoles();

        List<Role> employeeRoleList = roleEmployeeService.findRoleListByEmployeeId(id);

        model.addAttribute("roleList",roleList);
        model.addAttribute("employee",employee);
        model.addAttribute("employeeRoleList",employeeRoleList);
        return "manage/employee/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editEmployee(Employee employee,Integer[] roleId, RedirectAttributes redirectAttributes){
        roleEmployeeService.editEmployee(employee,roleId);

        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/manage/employee";
    }

    @GetMapping("/{id:\\d+}/error")
    public String errorEmployee(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        Employee employee = roleEmployeeService.findEmployeeById(id);

        employee.setState(Employee.EMPLOYEE_STATE_UNNOMAL);

        roleEmployeeService.updateSateError(employee);

        redirectAttributes.addFlashAttribute("message","状态已禁用");
        return"redirect:/manage/employee";
    }

    @GetMapping("/{id:\\d+}/success")
    public String successEmployee(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        Employee employee = roleEmployeeService.findEmployeeById(id);

        employee.setState(Employee.EMPLOYEE_STATE_NOMAL);

        roleEmployeeService.updateSateError(employee);

        redirectAttributes.addFlashAttribute("message","状态已正常");
        return"redirect:/manage/employee";
    }
}
