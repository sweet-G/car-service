package com.zt.erp.service.Impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.EmployeeRoleMapper;
import com.zt.erp.mapper.PermissionMapper;
import com.zt.erp.mapper.RoleMapper;
import com.zt.erp.mapper.RolePermissionMapper;
import com.zt.erp.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/27
 */

public class RolePermissionServiceImpl implements RolePermissionService {

    Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

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
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        List<Permission> endList = new ArrayList<>();

       treeList(permissionList,endList,0);
       return endList;
    }

    /**
     * 将查询数据库的角色列表转换为树形集合结果
     * @param sourceList 数据库查询出的集合
     * @param endList 转换结束的结果集合
     * @param parentId 父ID
     */
    private void treeList(List<Permission> sourceList, List<Permission> endList, int parentId) {
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, new Predicate<Permission>() {
            @Override
            public boolean apply(Permission permission) {
                return permission.getPid().equals(parentId);

            }}));

        for(Permission permission : tempList) {
            endList.add(permission);
            treeList(sourceList,endList,permission.getId());
        }
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
        if(permission.getPermissionType().equals(Permission.PERMISSION_TYPE_MENU)){
            permission.setPermissionType(Permission.PERMISSION_TYPE_BUTTON);
        }else{
            permission.setPermissionType(Permission.PERMISSION_TYPE_MENU);
        }
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
    public void saveRole(Role role, Integer[] permissionIds) throws ServiceException{
        // 新增角色
        roleMapper.insertSelective(role);

        if(permissionIds == null){
            throw  new ServiceException("请添加权限");
        }else{
            // 新增角色权限关联关系
            for(Integer permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);

                rolePermissionMapper.insert(rolePermission);
            }
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
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRole(Integer id) {

        //判断盖角色是否被员工拥有
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andRoleIdEqualTo(id);
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);

        if(employeeRoleList != null && employeeRoleList.size() > 0){
            throw new ServiceException("该角色被员工引用，不能删除");
        }

        //删除角色与权限的关联关系表
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(id);

        rolePermissionMapper.deleteByExample(rolePermissionExample);

        //删除角色
        Role role = roleMapper.selectByPrimaryKey(id);
        if(role != null){
            roleMapper.deleteByPrimaryKey(id);
            logger.info("删除角色{} ", role);
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
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRole(Role role,Integer[] permissionIds) {
        //将角色原有的权限的关联关系表删除
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionMapper.deleteByExample(rolePermissionExample);

        //重建角色和权限
        for(Integer permissionId : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);

            rolePermissionMapper.insertSelective(rolePermission);
        }
        //修改角色
        roleMapper.updateByPrimaryKeySelective(role);
        logger.info("修改角色{} ",role);
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

    /**
     * 获得角色的权限列表并查看是否checked
     * @param rolePermissionList 当前角色拥有的权限
     * @return 有序的map集合，如果是true则被选中，反之
     */
    @Override
    public Map<Permission, Boolean> permissionBooleanMap(List<Permission> rolePermissionList) {
        // 获得所有的权限列表
        List<Permission> permissionList = findList();

        // 获得有序的map集合
        Map<Permission,Boolean> resultMap = Maps.newLinkedHashMap();

        for(Permission permission : permissionList) {
            boolean flag = false;
            for(Permission rolePermission : rolePermissionList) {
                if(permission.getId().equals(rolePermission.getId())) {
                    flag = true;
                    break;
                }
            }
            resultMap.put(permission,flag);
        }
        return resultMap;
    }

    /**
     * 根据id查找角色并附带上权限列表
     *
     * @param id
     * @return
     */
    @Override
    public Role findRoleWithPermission(Integer id) {
        Role role = roleMapper.findByIdWithPermission(id);
        return role;
    }

    /**
     * 根据角色id查找对应权限
     *
     * @param id
     * @return
     */
    @Override
    public List<Permission> findPermissionByRolesId(Integer id) {
        return permissionMapper.findAllByRolesId(id);
    }


}
