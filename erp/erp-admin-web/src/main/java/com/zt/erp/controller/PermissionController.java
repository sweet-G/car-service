package com.zt.erp.controller;

import com.google.common.collect.Lists;
import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Permission;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.RolePermissionService;
import com.zt.erp.shiro.CustomerFilterChainDefinition;
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
    @Autowired
    private CustomerFilterChainDefinition customerFilterChainDefinition;

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

        //刷新权限
        customerFilterChainDefinition.updatePemission();
        return "redirect:/manage/permission";
    }

    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delPermission(@PathVariable Integer id){
        try {
            rolePermissionService.delPemission(id);
            //刷新权限
            customerFilterChainDefinition.updatePemission();
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editPermission(@PathVariable Integer id, Model model){
        Permission permission = rolePermissionService.findByPermissionId(id);
        List<Permission> menuPermissionList = rolePermissionService.findPermissionListByType(Permission.PERMISSION_TYPE_MENU);

        //排除当前的permission对象及其子对象
        //menuPermissionList.remove(permission);
        remove(menuPermissionList,permission);

        model.addAttribute("permission",permission);
        model.addAttribute("menuPermissionList",menuPermissionList);

        return "manage/permission/edit";

    }

    /**
     * 递归去除所有的子权限
     * @param menuPermissionList
     * @param permission
     */
    private void remove(List<Permission> menuPermissionList, Permission permission) {
        //通过临时变量对所有的List进行存储，防止侧漏
        List<Permission> tempList = Lists.newArrayList(menuPermissionList);

        for(int i = 0; i < tempList.size(); i++){
            //判断是否有子权限要去除
            if(tempList.get(i).getPid().equals(permission.getId())){
                remove(menuPermissionList,tempList.get(i));
            }
        }

        //去除跟权限
        menuPermissionList.remove(permission);
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
            //刷新权限
            customerFilterChainDefinition.updatePemission();

            redirectAttributes.addFlashAttribute("message","修改成功");
        }

        return "redirect:/manage/permission";
    }

}
