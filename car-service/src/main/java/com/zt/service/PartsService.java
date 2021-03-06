package com.zt.service;

import com.github.pagehelper.PageInfo;
import com.zt.entity.Parts;
import com.zt.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
     * @param pageNo
     * @return PageInfo<Parts>
     */
    PageInfo<Parts> findPage(Integer pageNo);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void del(Integer id);

    /**
     *
     * @return
     */
    List<Type> findTypeList();

    /**
     * parts模糊查询
     * @param pageNo
     * @param maps
     * @return
     */
    PageInfo<Parts> findPageWithTypeMap(Integer pageNo, Map<String,Object> maps);

    /**
     * 根据id更新
     * @param parts
     */
    void update(Parts parts);

    /**
     * 新增
     * @param parts
     */
    void saveParts(Parts parts);

    /**
     * 验证partsNo是否重复
     * @param partNo
     */
    boolean findByPartsNo(String partNo);
}
