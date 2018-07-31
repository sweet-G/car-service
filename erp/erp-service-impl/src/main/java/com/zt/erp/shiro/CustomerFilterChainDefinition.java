package com.zt.erp.shiro;

import com.zt.erp.entity.Permission;
import com.zt.erp.service.RolePermissionService;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/31
 */

public class CustomerFilterChainDefinition implements FactoryBean<Ini.Section> {

    @Autowired
    private RolePermissionService rolePermissionService;

    private String filterChainDefinitions;

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws Exception {

       Ini ini = new Ini();
       ini.load(filterChainDefinitions);

       Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

       List<Permission> permissionList = rolePermissionService.findList();

       for(Permission permission : permissionList){
           section.put(permission.getUrl(),  "perms[" +permission.getPermissionCode() + "]");
       }
        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
