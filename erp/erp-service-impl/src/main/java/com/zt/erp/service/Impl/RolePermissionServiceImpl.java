package com.zt.erp.service.Impl;

import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.EmployeeRoleMapper;
import com.zt.erp.mapper.PermissionMapper;
import com.zt.erp.mapper.RoleMapper;
import com.zt.erp.mapper.RolePermissionMapper;
import com.zt.erp.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/27
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 新增权限
     *
     * @param permission
     */
    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 类型是菜单权限的集合
     *
     * @param permissionTypeMenu
     * @return
     */
    @Override
    public List<Permission> findPermissionListByType(String permissionTypeMenu) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionTypeMenu);

       return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 权限列表
     *
     * @return
     */
    @Override
    public List<Permission> findList() {
        PermissionExample permissionExample = new PermissionExample();
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @throws SecurityException
     */
    @Override
    public void delPemission(Integer id) throws ServiceException{
        //判断是否该权限下有子权限
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(id);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        if(permissionList != null && permissionList.size() > 0){
            throw new ServiceException("该权限下拥有子权限，不能删除");
        }

        //判断当前权限是否被角色使用
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(id);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        if(rolePermissionList != null && rolePermissionList.size() > 0){
            throw new ServiceException("该权限被某个角色使用，不能删除");
        }

        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * g根据id查找权限
     *
     * @param id
     * @return
     */
    @Override
    public Permission findByPermissionId(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改权限
     *
     * @param permission
     */
    @Override
    public void editPermission(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 新增角色
     *
     * @param role
     * @param permissionIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRole(Role role, Integer[] permissionIds) {
        // 新增角色
        roleMapper.insertSelective(role);
        // 新增角色权限关联关系
        for(Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);

            rolePermissionMapper.insert(rolePermission);
        }
    }

    /**
     * 查询所有带权限的角色列表
     *
     * @return
     */
    @Override
    public List<Role> findRoleListWithPermission() {
       List<Role> roleList = roleMapper.findListWithPermission();
        return roleList;
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    public void delRole(Integer id) {
        //判断盖角色是否被员工拥有
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(id);
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);

        if(employeeRoleList != null && employeeRoleList.size() > 0){
            throw new ServiceException("该权限被某个角色使用，不能删除");
        }

        employeeRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查找role
     *
     * @param id
     * @return
     */
    @Override
    public Role findByRoleId(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改role
     *
     * @param role
     */
    @Override
    public void editRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 根据权限名称查找
     *
     * @param permissionName
     * @return
     */
    @Override
    public Permission findByPermissionName(String permissionName) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionNameEqualTo(permissionName);

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        Permission permission = null;
        if(permissionList.size() != 0){
            permission = permissionList.get(0);
        }

        return permission;
    }

    /**
     * 根据角色名称查找
     *
     * @param roleName
     * @return
     */
    @Override
    public Role findByRoleName(String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(roleName);

        List<Role> roleList = roleMapper.selectByExample(roleExample);

       Role role= null;
        if(roleList.size() != 0){
            role = roleList.get(0);
        }

        return role;
    }

    /**
     * 根据角色id查找对应权限
     *
     * @param id
     * @return
     */
    @Override
    public List<RolePermission> findRolePession(Integer id) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(id);

        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        return rolePermissionList;
    }

}
