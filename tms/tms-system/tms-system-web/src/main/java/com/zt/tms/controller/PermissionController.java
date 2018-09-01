package com.zt.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.zt.tms.controller.exception.NotFoundException;
import com.zt.tms.dto.ResponseBean;
import com.zt.tms.entity.Permission;
import com.zt.tms.exception.ServiceException;
import com.zt.tms.service.RolePermissionService;
import com.zt.tms.shiro.CustomerFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/31
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {
    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;
    @Autowired
    private CustomerFilterChainDefinition customerFilterChainDefinition;

    /**
     * 权限的首页
     * @return
     */
    @GetMapping
    public String home(Model model) {
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList",permissionList);
        return "manage/permission/home";
    }

    /**
     * 新增权限
     * @return
     */
    @GetMapping("/new")
    public String newPermission(Model model) {
        //查询菜单类型的权限列表
        List<Permission> permissionList = rolePermissionService.findPermissionByPermissionType(Permission.MENU_TYPE);
        model.addAttribute("permissionList",permissionList);
        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String newPermission(Permission permission, RedirectAttributes redirectAttributes) {
        rolePermissionService.savePermission(permission);
        //刷新Shiro的权限
        customerFilterChainDefinition.updateUrlPermission();
        redirectAttributes.addFlashAttribute("message","新增权限成功");
        return "redirect:/manage/permission";
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}/del")
    public @ResponseBody
    ResponseBean deletePermission(@PathVariable Integer id) {
        try {
            rolePermissionService.delPermissionById(id);
            //刷新Shiro的权限
            customerFilterChainDefinition.updateUrlPermission();
            return ResponseBean.success();
        } catch (ServiceException ex) {
            return ResponseBean.error(ex.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/edit")
    public String permissionEdit(@PathVariable Integer id,Model model){
        Permission permission = rolePermissionService.findPermissionById(id);

        if(permission == null) {
            throw new NotFoundException();
        }

        List<Permission> menuPermissionList = rolePermissionService.findPermissionByPermissionType(Permission.MENU_TYPE);

        //递归排除当前子权限
        remove(menuPermissionList,permission);

        model.addAttribute("menuPermissionList", menuPermissionList);
        model.addAttribute("permission", permission);
        return "manage/permission/edit";
    }

    private void remove(List<Permission> menuPermissionList, Permission permission) {
        List<Permission> temp = Lists.newArrayList(menuPermissionList);
        for(int i = 1; i < temp.size(); i++){
            if(temp.get(i).getParentId().equals(permission.getId())){
                remove(menuPermissionList,temp.get(i));
            }
        }

        menuPermissionList.remove(permission);

    }

    @PostMapping("/{id:\\d+}/edit")
    public String permissionEdit(Permission permission) {
        rolePermissionService.editPermission(permission);
        // 刷新权限
        customerFilterChainDefinition.updateUrlPermission();
        return "redirect:/manage/permission";
    }
}
