package com.cloudbest.search.repository;

import com.cloudbest.search.entity.EsItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

/**
 * 商品ES操作类
 * @author : hdq
 * @date 2020/7/11 11:31
 */
@Service
public interface EsItemsRepository extends ElasticsearchRepository<EsItems, Long> {
    /**
     * 搜索查询
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return
     */
    Page<EsItems> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);

}
