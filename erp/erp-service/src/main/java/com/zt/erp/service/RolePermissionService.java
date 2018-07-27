package com.zt.erp.service;

import com.zt.erp.entity.Permission;
import com.zt.erp.entity.Role;
import com.zt.erp.entity.RolePermission;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/27
 */

public interface RolePermissionService {
    /**
     * 新增权限
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 类型是菜单权限的集合
     * @param permissionTypeMenu
     * @return
     */
    List<Permission> findPermissionListByType(String permissionTypeMenu);

    /**
     * 权限列表
     * @return
     */
    List<Permission> findList();

    /**
     * 根据id删除
     * @param id
     * @throws SecurityException
     */
    void delPemission(Integer id) throws SecurityException;

    /**
     * g根据id查找权限
     * @param id
     * @return
     */
    Permission findByPermissionId(Integer id);

    /**
     * 修改权限
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 新增角色
     * @param role
     * @param permissionIds
     */
    void saveRole(Role role, Integer[] permissionIds);

    /**
     * 查询所有带权限的角色列表
     * @return
     */
    List<Role> findRoleListWithPermission();

    /**
     * 删除角色
     * @param id
     */
    void delRole(Integer id);

    /**
     * 根据id查找role
     * @param id
     * @return
     */
    Role findByRoleId(Integer id);

    /**
     * 修改role
     * @param role
     */
    void editRole(Role role);

    /**
     * 根据权限名称查找
     * @param permissionName
     * @return
     */
    Permission findByPermissionName(String permissionName);

    /**
     * 根据角色名称查找
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);

    /**
     * 根据角色id查找对应权限
     * @param id
     * @return
     */
    List<RolePermission> findRolePession(Integer id);
}
