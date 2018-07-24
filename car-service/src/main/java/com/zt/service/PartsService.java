package com.zt.service;

import com.github.pagehelper.PageInfo;
import com.zt.entity.Parts;
import org.springframework.stereotype.Service;

/**
 * @author zhangtian
 * @date 2018/7/23
 */

@Service
public interface PartsService {

    /**
     * 根据id查找parts
     * @param id
     * @return parts
     */
    Parts findById(Integer id);

    /**
     * 分页查询Parts集合
     * @return PageInfo<Parts>
     */
    PageInfo<Parts> findPage(Integer pageNo);
}
