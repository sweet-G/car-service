package com.zt.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.zt.tms.entity.*;
import com.zt.tms.exception.ServiceException;
import com.zt.tms.mapper.AccountRolesMapper;
import com.zt.tms.mapper.PermissionMapper;
import com.zt.tms.mapper.RolesMapper;
import com.zt.tms.mapper.RolesPermissionMapper;
import com.zt.tms.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/30
 */
@Service(version = "1.0" ,timeout = 5000)
public class RolePermissionServiceImpl implements RolePermissionService {

    private Logger logger = LoggerFactory.getLogger(RolePermissionService.class);

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolesPermissionMapper rolesPermissionMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private AccountRolesMapper accountRolesMapper;

    /**
     * 查询所有权限
     *
     * @return
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        List<Permission> resultList = new ArrayList<>();
        treeList(permissionList,resultList,0);
        return resultList;
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
                return permission.getParentId().equals(parentId);
            }
        }));

        for(Permission permission : tempList) {
            endList.add(permission);
            treeList(sourceList,endList,permission.getId());
        }
    }

    /**
     * 查询菜单类型
     *
     * @param permissionType
     * @return
     */
    @Override
    public List<Permission> findPermissionByPermissionType(String permissionType) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionType);
        return permissionMapper.selectByExample(permissionExample);
    }

    /**
     * 新增权限
     *
     * @param permission
     */
    @Override
    public void savePermission(Permission permission) {
        permission.setCreateTime(new Date());
        permissionMapper.insertSelective(permission);
        logger.info("添加权限{}",permission);
    }

    /**
     * 删除权限
     *
     * @param id
     */
    @Override
    public void delPermissionById(Integer id) {
        //查询该权限是否有子节点
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andParentIdEqualTo(id);

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && !permissionList.isEmpty()) {
            throw new ServiceException("该权限下有子节点，删除失败");
        }

        //查询权限是否被角色使用
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andPermissionIdEqualTo(id);

        List<RolesPermissionKey> rolesPermissionKeyList = rolesPermissionMapper.selectByExample(rolesPermissionExample);
        if(rolesPermissionKeyList != null && !rolesPermissionKeyList.isEmpty()) {
            throw new ServiceException("该权限被角色引用，删除失败");
        }
        //如果没有被使用，则可以直接删除
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        permissionMapper.deleteByPrimaryKey(id);
        logger.info("删除权限 {}",permission);
    }

    /**
     * 根据id查找对应的权限
     *
     * @param id
     * @return
     */
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改权限
     *
     * @param permission
     */
    @Override
    public void editPermission(Permission permission) {
        if(permission.getPermissionType().equals(Permission.MENU_TYPE)){
            permission.setPermissionType(Permission.BUTTON_TYPE);
        }else{
            permission.setPermissionType(Permission.MENU_TYPE);
        }
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 查询所有角色对应的权限
     *
     * @return
     */
    @Override
    public List<Roles> findAllRolesWithPermission() {
        return rolesMapper.findAllWithPermission();
    }

    /**
     * 新增角色
     *
     * @param roles
     * @param permissionId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRoles(Roles roles, Integer[] permissionId) {
        //保存角色
        roles.setCreateTime(new Date());
        rolesMapper.insertSelective(roles);

        //保存角色权限关联关系表
        if(permissionId != null) {
            for (Integer perId : permissionId) {
                RolesPermissionKey rolesPermissionKey = new RolesPermissionKey();
                rolesPermissionKey.setPermissionId(perId);
                rolesPermissionKey.setRolesId(roles.getId());

                rolesPermissionMapper.insert(rolesPermissionKey);
            }
        }
        logger.info("保存角色 {}",roles);
    }

    /**
     * 根据id查找角色和角色对应的权限
     *
     * @param id
     * @return
     */
    @Override
    public Roles findRolesWithPermissionById(Integer id) {
        return rolesMapper.findByIdWithPermission(id);
    }

    /**
     * 修改角色
     *
     * @param roles
     * @param permissionId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateRoles(Roles roles, Integer[] permissionId) {
        //将角色原有和权限的对应关系删除
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andRolesIdEqualTo(roles.getId());

        rolesPermissionMapper.deleteByExample(rolesPermissionExample);
        //重建角色和权限的对应关系
        for(Integer perId : permissionId) {
            RolesPermissionKey rolesPermissionKey = new RolesPermissionKey();
            rolesPermissionKey.setRolesId(roles.getId());
            rolesPermissionKey.setPermissionId(perId);
            rolesPermissionMapper.insert(rolesPermissionKey);
        }
        //修改角色对象
        rolesMapper.updateByPrimaryKeySelective(roles);

        logger.info("修改角色 {}",roles);
    }

    /**
     * 删除角色
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRolesById(Integer id) {
        //查询是否被账号引用，如果引用则不能删除
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andRolesIdEqualTo(id);

        List<AccountRolesKey> accountRolesKeys = accountRolesMapper.selectByExample(accountRolesExample);
        if(accountRolesKeys != null && !accountRolesKeys.isEmpty()) {
            throw new ServiceException("该角色被账号引用，删除失败");
        }
        //删除角色和权限的关系记录
        RolesPermissionExample rolesPermissionExample = new RolesPermissionExample();
        rolesPermissionExample.createCriteria().andRolesIdEqualTo(id);

        rolesPermissionMapper.deleteByExample(rolesPermissionExample);
        //删除角色
        Roles roles = rolesMapper.selectByPrimaryKey(id);
        rolesMapper.deleteByPrimaryKey(id);

        logger.info("删除角色 {}",roles);
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Roles> findAllRoles() {
        RolesExample rolesExample = new RolesExample();
        return rolesMapper.selectByExample(rolesExample);
    }

    /**
     * 根据id查询当前账号所拥有的角色
     *
     * @param id
     * @return
     */
    @Override
    public List<Roles> findRolesByAccountId(Integer id) {
        return rolesMapper.findRolesByAccountId(id);
    }

    /**
     * 根据角色id查找权限
     *
     * @param rolesId
     * @return
     */
    @Override
    public List<Permission> findAllPermissionByRolesId(Integer rolesId) {
        return permissionMapper.findAllByRolesId(rolesId);
    }

}
