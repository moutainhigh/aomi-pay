package com.cloudbest.search.service.impl;

import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.PageResult;
import com.cloudbest.common.util.CommonExceptionUtils;
import com.cloudbest.common.util.GeneralConvertorUtil;
import com.cloudbest.search.entity.EsItems;
import com.cloudbest.search.mapper.EsItemsMapper;
import com.cloudbest.search.repository.EsItemsRepository;
import com.cloudbest.search.service.EsItemsService;
import com.cloudbest.search.vo.EsItemsVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;

/**
 * 商品搜索管理Service实现类
 *
 * @author : hdq
 * @date 2020/7/11 13:33
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EsItemsServiceImpl implements EsItemsService {

    @Autowired
    private EsItemsMapper esItemsMapper;

    @Autowired
    private EsItemsRepository esItemsRepository;


    @Override
    public int importAll() {
        List<EsItems> esItemstList = esItemsMapper.getAllEsItemsList(null);
        log.debug("itemsMapper.getAllEsItemsList：{}", esItemstList);
        //导入数据count
        int count = 0;
        if (esItemstList.isEmpty()) {
            return count;
        }
        Iterable<EsItems> esProductIterable = esItemsRepository.saveAll(esItemstList);
        log.debug("itemsRepository.saveAll：{}", esItemstList);
        Iterator<EsItems> iterator = esProductIterable.iterator();

        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;

    }

    @Override
    public void deleteById(Integer id) {
        esItemsRepository.deleteById(id.longValue());
    }


    @Override
    public void deleteByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids) || ids.isEmpty()) {
            CommonExceptionUtils.throwParamException(CommonErrorCode.E_ITEMS_IDS_NULL);
        }
        List<EsItems> esItemsList = new ArrayList<>();
        for (Integer id : ids) {
            if (id == null) {
                CommonExceptionUtils.throwParamException(CommonErrorCode.E_ITEMS_ID_NULL);
            }
            EsItems esItems = new EsItems();
            esItems.setId(id);
            esItemsList.add(esItems);
        }
        esItemsRepository.deleteAll(esItemsList);
    }


    @Override
    public EsItems create(Integer id) {
        EsItems result = null;
        List<EsItems> esItemsList = esItemsMapper.getAllEsItemsList(id.longValue());
        if (!esItemsList.isEmpty()) {
            EsItems esItems = esItemsList.get(0);
            result = esItemsRepository.save(esItems);
        }
        return result;
    }

    @Override
    public PageResult search(String keyword, Integer pageNum, Integer pageSize) {
        //page是从0开始的，要减1
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        //搜索查询
        Page<EsItems> esItemsPage = esItemsRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);

        if (esItemsPage.isEmpty()) {
            return null;
        }
        PageResult esItemsResult = new PageResult();
        esItemsResult.setCount(esItemsPage.getTotalElements());
        esItemsResult.setPageNo(String.valueOf(esItemsPage.getNumber()));
        esItemsResult.setPageSize(String.valueOf(esItemsPage.getSize()));
        List<EsItemsVO> EsItemsVOList = pageToVo(esItemsPage);
        esItemsResult.setResult(pageToVo(esItemsPage));

        log.info("EsItemsVOList:{}", EsItemsVOList.toString());
        return esItemsResult;

    }

    @Override
    public PageResult search(String keyword, Integer categoryId, Integer pageNo, Integer pageSize, Integer sort) {
        //page是从0开始的，要减1
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //过滤
        if (categoryId != null) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("firstCategoryId", categoryId));
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }

        //搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //排序
        if (sort == 1) {
            //按销量从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("spuSaltVolume").order(SortOrder.DESC));
        } else if (sort == 2) {
            //按价格从低到高
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("salePrice").order(SortOrder.ASC));
        } else if (sort == 3) {
            //按价格从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("salePrice").order(SortOrder.DESC));
        } else {
            //按相关度
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        log.info("DSL:{}", searchQuery.getQuery().toString());
        Page<EsItems> esItemsPage = esItemsRepository.search(searchQuery);

        if (esItemsPage.isEmpty()) {
            return null;
        }
        PageResult esItemsResult = new PageResult();
        esItemsResult.setCount(esItemsPage.getTotalElements());
        esItemsResult.setPageNo(String.valueOf(esItemsPage.getNumber()));
        esItemsResult.setPageSize(String.valueOf(esItemsPage.getSize()));
        List<EsItemsVO> EsItemsVOList = pageToVo(esItemsPage);
        esItemsResult.setResult(pageToVo(esItemsPage));

        log.info("EsItemsVOList:{}", EsItemsVOList.toString());
        return esItemsResult;
    }

    /**
     * Desc:
     * 〈〉
     *
     * @param esItemsPage 1
     * @return : java.util.List<com.cloudbest.search.vo.EsItemsVO>
     * @author : hdq
     * @date : 2020/7/22 18:08
     */
    private List<EsItemsVO> pageToVo(Page<EsItems> esItemsPage) {
        List<EsItems> esItemsList = esItemsPage.getContent();
        List<EsItemsVO> EsItemsVOList = new LinkedList<EsItemsVO>();

        log.info("esItemsList:{}", esItemsList);
        esItemsList.forEach(esItems -> {
            EsItemsVO esItemsVO = GeneralConvertorUtil.convertor(esItems, EsItemsVO.class);
            EsItemsVOList.add(esItemsVO);
        });
        return EsItemsVOList;
    }
}



























