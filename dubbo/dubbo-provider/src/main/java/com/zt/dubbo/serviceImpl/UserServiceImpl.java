package com.zt.dubbo.serviceImpl;

import com.zt.dubbo.service.UserService;

/**
 * @author zhangtian
 * @date 2018/8/11
 */

public class UserServiceImpl implements UserService {
    public String findById(Integer id) {
        if(id.equals(1000)){
            return "rose,hanks";
        }else {
            return "alex";
        }
    }
}
