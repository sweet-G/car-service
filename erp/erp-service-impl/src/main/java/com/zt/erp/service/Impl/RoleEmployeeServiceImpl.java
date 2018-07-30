package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.*;
import com.zt.erp.mapper.EmployeeMapper;
import com.zt.erp.mapper.EmployeeRoleMapper;
import com.zt.erp.mapper.RoleMapper;
import com.zt.erp.service.RoleEmployeeService;
import com.zt.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/28
 */
@Service
public class RoleEmployeeServiceImpl implements RoleEmployeeService {

    Logger logger = LoggerFactory.getLogger(RoleEmployeeServiceImpl.class);

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所得角色列表
     *
     * @return
     */
    @Override
    public List<Role> findAllRoles() {
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        return roleList;
    }

    /**
     * 新增员工
     *
     * @param employee
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveEmployee(Employee employee, Integer[] roleIds) {

        String codePassword = DigestUtils.md5Hex(employee.getPassword());
        employee.setPassword(codePassword);

        //账号默认状态
        employee.setState(Employee.EMPLOYEE_STATE_NOMAL);

        employeeMapper.insertSelective(employee);

        for(Integer roleId :roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);

            employeeRoleMapper.insertSelective(employeeRole);
        }
        logger.info("新增员工{} ",employee);
    }

    /**
     * 根据账号或手机号分页
     *
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<Employee> findPageWithMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<Employee> employeeList = employeeMapper.findPageWithRoleMaps(maps);

        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return pageInfo;
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delEmployee(Integer id) {
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(id);

        employeeRoleMapper.deleteByExample(employeeRoleExample);

        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee != null){
            employeeMapper.deleteByPrimaryKey(id);
        }
        logger.info("删除员工 {}",employee);
    }

    /**
     * 修改员工
     *
     * @param employee
     */
    @Override
    public void editEmployee(Employee employee,Integer[] roleIds) {
        //删除员工与角色的关联关系
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employee.getId());
        employeeRoleMapper.deleteByExample(employeeRoleExample);

        //重建员工与角色关联关系
        for(Integer roleId :roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);

            employeeRoleMapper.insert(employeeRole);
        }
        //修改员工
        employeeMapper.updateByPrimaryKeySelective(employee);
        logger.info("修改员工 {}",employee);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id查询当前员工的角色
     *
     * @param id
     * @return
     */
    @Override
    public List<Role> findRoleListByEmployeeId(Integer id) {
        return roleMapper.findListByEmployeeId(id);
    }

    /**
     *修改状态
     * @param employee
     */
    @Override
    public void updateSateError(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }


}
