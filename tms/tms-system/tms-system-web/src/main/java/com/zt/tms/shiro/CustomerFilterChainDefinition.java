package com.zt.tms.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zt.tms.entity.Permission;
import com.zt.tms.service.RolePermissionService;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/30
 */

public class CustomerFilterChainDefinition {
    private Logger logger = LoggerFactory.getLogger(CustomerFilterChainDefinition.class);

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    private Map<String,String> filterChainDefinitions;
    private AbstractShiroFilter shiroFilter;


    public void setFilterChainDefinitions(Map<String, String> filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }

    /**
     * Spring容器启动时调用
     */
    @PostConstruct
    public synchronized void init() {
        logger.info("------初始化URL权限-----------");
        //清除原有的URL权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的URL权限
        load();
        logger.info("------初始化URL权限结束-----------");
    }

    /**
     * 重新加载URL权限
     */
    public synchronized void updateUrlPermission() {
        logger.info("------刷新URL权限-----------");
        //清除原有的URL权限
        getFilterChainManager().getFilterChains().clear();
        //加载现有的URL权限
        load();
        logger.info("------刷新URL权限结束-----------");
    }

    /**
     * 加载URL和权限的对应关系
     */
    public synchronized void load() {
        LinkedHashMap<String,String> urlMap = new LinkedHashMap<>();
        //加载静态资源
        urlMap.putAll(filterChainDefinitions);

        //从数据库中查找所有的权限对象
        List<Permission> permissionList = rolePermissionService.findAllPermission();

        for(Permission permission : permissionList) {
            urlMap.put(permission.getUrl(),"perms["+permission.getPermissionCode()+"]");
        }
        urlMap.put("/**","user");

        //URL和权限的关系设置到shiroFilter中
        DefaultFilterChainManager defaultFilterChainManager = getFilterChainManager();
        for(Map.Entry<String,String> entry : urlMap.entrySet()) {
            defaultFilterChainManager.createChain(entry.getKey(),entry.getValue());
        }
    }


    private DefaultFilterChainManager getFilterChainManager() {
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        return defaultFilterChainManager;
    }

}
