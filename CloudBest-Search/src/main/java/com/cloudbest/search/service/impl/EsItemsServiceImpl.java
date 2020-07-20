package com.cloudbest.search.service.impl;

import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.PageResult;
import com.cloudbest.common.util.CommonExceptionUtils;
import com.cloudbest.search.entity.EsItems;
import com.cloudbest.search.mapper.EsItemsMapper;
import com.cloudbest.search.repository.EsItemsRepository;
import com.cloudbest.search.service.EsItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        if(esItemstList.isEmpty()){
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
        if(CollectionUtils.isEmpty(ids)||ids.isEmpty()){
            CommonExceptionUtils.throwParamException(CommonErrorCode.E_ITEMS_IDS_NULL);
        }
        List<EsItems> esItemsList = new ArrayList<>();
        for (Integer id : ids) {
            if(id == null){
                CommonExceptionUtils.throwParamException(CommonErrorCode.E_ITEMS_ID_NULL);
            }
            EsItems esItems = new EsItems();
            esItems.setId(id.intValue());
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
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<EsItems> esItemsList = esItemsRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
        log.info("esItemsList:{}",esItemsList.toString());
        return null;
    }

    @Override
    public Page<EsItems> search1(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esItemsRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}


























