package com.zt.erp.controller;

import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Permission;
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
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String home(Model model){
        List<Permission> permissionList = rolePermissionService.findList();
        model.addAttribute("permissionList",permissionList);

        return "manage/permission/home";
    }

    @GetMapping("/new")
    public String newPermission(Model model){
        //封装类型是菜单的集合
        List<Permission> menuPermissionList = rolePermissionService.findPermissionListByType(Permission.PERMISSION_TYPE_MENU);
        model.addAttribute("menuPermissionList",menuPermissionList);

        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String newPermission(Permission permission){
        rolePermissionService.addPermission(permission);
        return "redirect:/manage/permission";
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delPermission(@PathVariable Integer id){
        try {
            rolePermissionService.delPemission(id);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editPermission(@PathVariable Integer id, Model model){
        Permission permission = rolePermissionService.findByPermissionId(id);
        List<Permission> menuPermissionList = rolePermissionService.findPermissionListByType(Permission.PERMISSION_TYPE_MENU);

        model.addAttribute("permission",permission);
        model.addAttribute("menuPermissionList",menuPermissionList);

        return "manage/permission/edit";

    }

    @PostMapping("/{id:\\d+}/edit")
    public String editPermission( @PathVariable Integer id,String permissionName, RedirectAttributes redirectAttributes){
       Permission permission = rolePermissionService.findByPermissionName(permissionName);

        if(permission != null && id.equals(permission.getId())){
            redirectAttributes.addFlashAttribute("message","该权限已存在,不能修改");
        }else{
            permission = rolePermissionService.findByPermissionId(id);

            permission.setPermissionName(permissionName);
            rolePermissionService.editPermission(permission);

            redirectAttributes.addFlashAttribute("message","修改成功");
        }

        return "redirect:/manage/permission";
    }

}
