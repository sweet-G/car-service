package com.zt.erp.shiro;

import com.zt.erp.entity.Permission;
import com.zt.erp.service.RolePermissionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhangtian
 * @date 2018/7/31
 */

public class CustomerFilterChainDefinition{

    @Autowired
    private RolePermissionService rolePermissionService;

    private String filterChainDefinitions;

    private AbstractShiroFilter shiroFilter;

    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @PostConstruct
    public synchronized  void init(){
        System.out.println("~~~~~~~初始化~~~~~~~");
        //清除原有的权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的权限
        load();
    }

    public synchronized void updatePemission(){
        System.out.println("~~~~~更新中~~~~~~");
        //清除原有的权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的权限
        load();
    }

    public synchronized void load() {

       Ini ini = new Ini();
       ini.load(filterChainDefinitions);

       Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);

       List<Permission> permissionList = rolePermissionService.findList();

       for(Permission permission : permissionList){
           section.put(permission.getUrl(),  "perms[" +permission.getPermissionCode() + "]");
       }

       section.put("/**","user");

        DefaultFilterChainManager manager = getFilterChainManager();
        for(Ini.Section.Entry<String,String> entry : section.entrySet()){
            manager.createChain(entry.getKey(),entry.getValue());
        }
    }


    public DefaultFilterChainManager getFilterChainManager(){
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) this.shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();

        return defaultFilterChainManager;
    }

}
