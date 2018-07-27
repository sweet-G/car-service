package com.zt.erp.controller;

import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Permission;
import com.zt.erp.entity.Role;
import com.zt.erp.entity.RolePermission;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        Role role =rolePermissionService.findByRoleId(id);
        List<Permission> permissionList = rolePermissionService.findList();
        List<RolePermission> rolePermissionList = rolePermissionService.findRolePession(id);

        model.addAttribute("rolePermissionList",rolePermissionList);
        model.addAttribute("role",role);
        model.addAttribute("permissionList",permissionList);
        return "manage/role/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editRole( @PathVariable Integer id,String roleName, RedirectAttributes redirectAttributes){
        Role role = rolePermissionService.findByRoleName(roleName);

        if(role != null && id.equals(role.getId())){
            redirectAttributes.addFlashAttribute("message","该角色已存在,不能修改");
        }else{
            role = rolePermissionService.findByRoleId(id);

            role.setRoleName(roleName);
            rolePermissionService.editRole(role);
            redirectAttributes.addFlashAttribute("message","修改成功");
        }

        return "redirect:/manage/role";
    }

}
