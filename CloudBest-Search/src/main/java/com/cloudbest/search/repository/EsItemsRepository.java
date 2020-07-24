package com.cloudbest.search.repository;

import com.cloudbest.search.entity.EsItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 商品ES操作类
 * @author : hdq
 * @date 2020/7/11 11:31
 */
public interface EsItemsRepository extends ElasticsearchRepository<EsItems, Long> {
    /**
     * 搜索查询
     * @param name              商品名称
     * @param page              分页信息
     * @return Page<EsItems>
     */
    Page<EsItems> findByName(String name,Pageable page);
}
