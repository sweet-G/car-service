package com.zt.service;

import com.github.pagehelper.PageInfo;
import com.zt.entity.Type;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/7/25
 */
@Service
public interface TypeService {
    /**
     * 分页并模糊
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<Type> findPageWithMap(Integer pageNo, Map<String,Object> maps);

    /**
     * 根据id删除
     * @param id
     */
    void delTypeName(Integer id);

    /**
     * 根据id查找
     * @return id
     */
    Type findById(Integer id);

    /**
     * 新增
     * @param type
     */
    void saveType(Type type);
}
