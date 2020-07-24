package com.cloudbest.search.service.impl;

import com.cloudbest.common.constants.ParamConstans;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    @Resource
    private EsItemsMapper esItemsMapper;

    @Resource
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

        for (EsItems esItems : esProductIterable) {
            count++;
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
        EsItems result = new EsItems();
        List<EsItems> esItemsList = esItemsMapper.getAllEsItemsList(id.longValue());
        if (esItemsList.isEmpty()) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_ITEMS_IMPORT_ID);
        }else{
            EsItems esItems = esItemsList.get(0);
            result = esItemsRepository.save(esItems);
        }
        log.info("导入的单条商品记录:{}",result);
        return result;
    }

    @Override
    public PageResult search(String keyword, Integer pageNum, Integer pageSize) {
        //page是从0开始的，要减1
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<EsItems> esItemsPage = null;
        try {
            //搜索查询
            esItemsPage = esItemsRepository.findByName(keyword, pageable);
        } catch (Exception e) {
            CommonExceptionUtils.throwSystemException(CommonErrorCode.E_ITEMS_SEARCH);
        }

        PageResult esItemsResult = new PageResult();
        if (esItemsPage.isEmpty()) {
            return esItemsResult;
        }

        esItemsResult.setCount(esItemsPage.getTotalElements());
        esItemsResult.setPageNo(String.valueOf(esItemsPage.getNumber()));
        esItemsResult.setPageSize(String.valueOf(esItemsPage.getSize()));
        List<EsItemsVO> esItemsVOList = pageToVo(esItemsPage);
        esItemsResult.setResult(pageToVo(esItemsPage));

        log.info("EsItemsVOList:{}", esItemsVOList.toString());
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
        if (categoryId != null && categoryId != 0) {
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
        if (sort.equals(ParamConstans.SORT_VOLUME_DESC)) {
            //按销量从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("spuSaltVolume").order(SortOrder.DESC));
        } else if (sort.equals(ParamConstans.SORT_PRICE_ASC)) {
            //按价格从低到高
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("discountedPrice").order(SortOrder.ASC));
        } else if (sort.equals(ParamConstans.SORT_ZERO_DESC)) {
            //按价格从高到低
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("discountedPrice").order(SortOrder.DESC));
        } else {
            //按相关度
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        log.info("DSL:{}", searchQuery.getQuery().toString());

        Page<EsItems> esItemsPage = null;
        try {
            //搜索查询
            esItemsPage = esItemsRepository.search(searchQuery);
        } catch (Exception e) {
            CommonExceptionUtils.throwSystemException(CommonErrorCode.E_ITEMS_SEARCH);
        }

        PageResult esItemsResult = new PageResult();
        if (esItemsPage.isEmpty()) {
            return esItemsResult;
        }
        esItemsResult.setCount(esItemsPage.getTotalElements());
        esItemsResult.setPageNo(String.valueOf(esItemsPage.getNumber()));
        esItemsResult.setPageSize(String.valueOf(esItemsPage.getSize()));
        List<EsItemsVO> esItemsVOList = pageToVo(esItemsPage);
        esItemsResult.setResult(pageToVo(esItemsPage));

        log.info("EsItemsVOList:{}", esItemsVOList.toString());
        return esItemsResult;
    }

    /**
     * Desc:page<T> to List<T>
     *
     * @param esItemsPage 1
     * @return : List<EsItemsVO>
     * @author : hdq
     * @date : 2020/7/22 18:08
     */
    private List<EsItemsVO> pageToVo(Page<EsItems> esItemsPage) {
        List<EsItems> esItemsList = esItemsPage.getContent();
        List<EsItemsVO> esItemsVOList = new LinkedList<>();

        log.info("esItemsList:{}", esItemsList);
        esItemsList.forEach(esItems -> {
            EsItemsVO esItemsVO = GeneralConvertorUtil.convertor(esItems, EsItemsVO.class);
            esItemsVO.setDiscountedPrice(esItemsVO.getDiscountedPrice().setScale(2,BigDecimal.ROUND_HALF_EVEN));
            esItemsVOList.add(esItemsVO);
        });
        return esItemsVOList;
    }
}



























