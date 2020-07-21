package com.cloudbest.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.search.entity.EsItems;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Mapper
 * @author : hdq
 * @date 2020/7/11
 */
@Repository
public interface EsItemsMapper extends BaseMapper<EsItems> {

    /**
     * Desc: 查询所有商品相关数据
     *
     * @param id
     * @author : hdq
     * @date : 2020/7/14 11:13
     * @return List<EsItems>
     */
    List<EsItems> getAllEsItemsList(@Param("id") Long id);

}



























