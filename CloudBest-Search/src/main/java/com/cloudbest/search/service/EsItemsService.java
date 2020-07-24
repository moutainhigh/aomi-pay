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
     * @param id 商品spuid
     */
    void deleteById(Integer id);

    /**
     * 根据ids删除商品
     * @param ids 商品spuids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id导入商品到es
     * @param id 商品spuid
     * @return EsItems
     */
    EsItems create(Integer id);

    /**
     * 根据关键字搜索商品
     * @param keyword 关键字
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return PageResult
     */
    PageResult search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索商品
     * @param keyword 关键字
     * @param categoryId 分类id
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param sort 排序
     * @return PageResult
     */
    PageResult search(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, Integer sort);
}
