package com.zt.erp.service;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Employee;
import com.zt.erp.entity.EmployeeRole;
import com.zt.erp.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/28
 */

public interface RoleEmployeeService {
    /**
     * 查询所得角色列表
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 新增员工
     * @param employee
     * @param roleIds
     */
    void saveEmployee(Employee employee, Integer[] roleIds);

    /**
     * 根据账号或手机号分页
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<Employee> findPageWithMap(Integer pageNo, Map<String,Object> maps);

    /**
     * 根据id删除
     * @param id
     */
    void delEmployee(Integer id);

    /**
     * 修改员工
     * @param employee
     */
    void editEmployee(Employee employee,Integer[] roleIds);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Employee findEmployeeById(Integer id);

    /**
     * 根据id查询当前员工的角色
     * @param id
     * @return
     */
    List<Role> findRoleListByEmployeeId(Integer id);

    /**
     *修改状态
     * @param employee
     */
    void updateSateError(Employee employee);

}
