package com.zt.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zt.service.UserService;

/**
 * @author zhangtian
 * @date 2018/8/25
 *
*/

@Service(version = "1.0",timeout = 5000)
public class UserServiceImpl implements UserService {
    @Override
    public String findById(Integer id) {
        if(id.equals(1)){
            return "tom";
        }else{
            return "rose";
        }
    }
}
