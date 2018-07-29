package com.zt.erp.controller;

import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Permission;
import com.zt.erp.entity.Role;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/27
 */
@Controller
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String home(Model model){
        List<Role> roleList = rolePermissionService.findRoleListWithPermission();
        model.addAttribute("roleList",roleList);

        return "manage/role/home";
    }

    @GetMapping("/new")
    public String newRole(Model model){
        List<Permission> permissionList = rolePermissionService.findList();
        model.addAttribute("permissionList",permissionList);
        return "manage/role/new";
    }

    @PostMapping("/new")
    public String newRole(Role role, Integer[] permissionId){
        rolePermissionService.saveRole(role,permissionId);
        return "redirect:/manage/role";
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delRole(@PathVariable Integer id){

        try {
            rolePermissionService.delRole(id);
        } catch (ServiceException e) {
          return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editRole(@PathVariable Integer id, Model model){
        Role role =rolePermissionService.findRoleWithPermission(id);

        //获得是否被checked的权限列表
        Map<Permission, Boolean> permissionBooleanMap = rolePermissionService.permissionBooleanMap(role.getPermissionList());

        model.addAttribute("permissionBooleanMap",permissionBooleanMap);
        model.addAttribute("role",role);
        return "manage/role/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editRole(Role role, Integer[] permissionId){
        rolePermissionService.editRole(role,permissionId);
        return "redirect:/manage/role";
    }

}
