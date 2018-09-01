package com.zt.tms.service;

import com.zt.tms.entity.Permission;
import com.zt.tms.entity.Roles;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/30
 */

public interface RolePermissionService {
    /**
     * 查询所有权限
     * @return
     */
    List<Permission> findAllPermission();

    /**
     * 查询菜单类型
     * @param menuType
     * @return
     */
    List<Permission> findPermissionByPermissionType(String menuType);

    /**
     * 新增权限
     * @param permission
     */
    void savePermission(Permission permission);

    /**
     * 删除权限
     * @param id
     */
    void delPermissionById(Integer id);

    /**
     * 根据id查找对应的权限
     * @param id
     * @return
     */
    Permission findPermissionById(Integer id);

    /**
     * 修改权限
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 查询所有角色对应的权限
     * @return
     */
    List<Roles> findAllRolesWithPermission();

    /**
     * 新增角色
     * @param roles
     * @param permissionId
     */
    void saveRoles(Roles roles, Integer[] permissionId);

    /**
     * 根据id查找角色和角色对应的权限
     * @param id
     * @return
     */
    Roles findRolesWithPermissionById(Integer id);

    /**
     * 修改角色
     * @param roles
     * @param permissionId
     */
    void updateRoles(Roles roles, Integer[] permissionId);

    /**
     * 删除角色
     * @param id
     */
    void delRolesById(Integer id);

    /**
     * 查询所有角色
     * @return
     */
    List<Roles> findAllRoles();

    /**
     * 根据id查询当前账号所拥有的角色
     * @param id
     * @return
     */
    List<Roles> findRolesByAccountId(Integer id);

    /**
     * 根据角色id查找权限
     * @param id
     * @return
     */
    List<Permission> findAllPermissionByRolesId(Integer id);
}
