package com.cloudbest.search.service;


import com.cloudbest.common.domain.PageResult;
import com.cloudbest.search.entity.EsItems;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 商品搜索管理Service
 *
 * @author : hdq
 * @date 2020/7/11 13:33
 */
public interface EsItemsService {

    /**
     * 从数据库中导入所有商品到ES
     * @return int
     */
    int importAll();

    /**
     * 根据id删除商品
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据ids删除商品
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id创建商品
     * @param id
     * @return EsItems
     */
    EsItems create(Integer id);

    /**
     * 根据关键字搜索商品
     * @param keyword 关键字
     * @param pageNum
     * @param pageSize
     * @return PageResult
     */
    PageResult search(String keyword, Integer pageNum, Integer pageSize);

}
