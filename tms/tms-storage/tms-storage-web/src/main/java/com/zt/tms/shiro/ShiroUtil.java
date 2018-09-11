package com.zt.tms.shiro;

import com.zt.tms.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@Component
public class ShiroUtil {

    public Account getAccount(){
        Subject subject = SecurityUtils.getSubject();
        return (Account) subject.getPrincipal();
    }
}
