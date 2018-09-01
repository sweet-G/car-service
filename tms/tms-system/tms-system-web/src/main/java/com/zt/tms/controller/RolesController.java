package com.zt.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.zt.tms.controller.exception.NotFoundException;
import com.zt.tms.dto.ResponseBean;
import com.zt.tms.entity.Permission;
import com.zt.tms.entity.Roles;
import com.zt.tms.exception.ServiceException;
import com.zt.tms.service.RolePermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/31
 */
@Controller
@RequestMapping("/manage/roles")
public class RolesController {

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String home(Model model) {
        model.addAttribute("rolesList", rolePermissionService.findAllRolesWithPermission());
        return "manage/roles/home";
    }

    @GetMapping("/new")
    public String newRoles(Model model){
        List<Permission> permissionList = rolePermissionService.findAllPermission();

        model.addAttribute("permissionList",permissionList);
        return "manage/roles/new";
    }

    @PostMapping("/new")
    public String newRoles(Roles roles, Integer[] permissionId, RedirectAttributes redirectAttributes){
        rolePermissionService.saveRoles(roles,permissionId);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/manage/roles";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editRoles(@PathVariable Integer id,Model model){
        //查询角色和角色拥有的权限列表
        Roles roles = rolePermissionService.findRolesWithPermissionById(id);

        if(roles == null){
            throw  new NotFoundException();
        }

        List<Permission> permissionList = rolePermissionService.findAllPermission();

        Map<Permission, Boolean> map = checkPermissionList(roles.getPermissionList(),permissionList);

        model.addAttribute("roles",roles);
        model.addAttribute("permissionMap",map);
        return "manage/roles/edit";

    }

    /**
     * 在编辑页面判断当前权限的复选框是否被checked
     * @param rolesPermissionList 当前角色拥有的权限
     * @param permissionList 所有的权限列表
     * @return 有顺序的map集合，如果被选择则value为true
     */
    private Map<Permission,Boolean> checkPermissionList(List<Permission> rolesPermissionList, List<Permission> permissionList) {
        Map<Permission, Boolean> resultmap = Maps.newLinkedHashMap();

        for(Permission permission :permissionList){
            boolean falg = false;
            for(Permission rolePermission : rolesPermissionList){
                if(permission.getId().equals(rolePermission.getId())){
                    falg = true;
                    break;
                }
            }
            resultmap.put(permission,falg);
        }

        return resultmap;
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editRoles(Roles roles,Integer[] permissionId,
                            RedirectAttributes redirectAttributes) {
        rolePermissionService.updateRoles(roles,permissionId);

        redirectAttributes.addFlashAttribute("message","角色修改成功");
        return "redirect:/manage/roles";
    }

    @GetMapping("/{id:\\d+}/del")
    public @ResponseBody
    ResponseBean deleteRoles(@PathVariable Integer id) {
        try {
            rolePermissionService.delRolesById(id);
            return ResponseBean.success();
        } catch (ServiceException ex) {
            return ResponseBean.error(ex.getMessage());
        }
    }


}
