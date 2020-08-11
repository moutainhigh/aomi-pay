package com.aomi.pay.service.impl;

import com.aomi.pay.entity.DictionaryBankArea;
import com.aomi.pay.mapper.DictionaryBankAreaMapper;
import com.aomi.pay.service.DictionaryBankAreaService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.vo.DictionaryBankAreaVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 银行区域编码数据字典Service实现类
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryBankAreaServiceImpl implements DictionaryBankAreaService {

    @Resource
    private DictionaryBankAreaMapper dictionaryBankAreaMapper;

    /**
     * 获取所有银行编码信息
     */
    @Cacheable(value = "bankAreaCache", key = "'all'")
    @Override
    public List<DictionaryBankAreaVO> getAllBank() {
        //获取所有的银行编码信息
        List<DictionaryBankArea> dictionaryBankAreaList = dictionaryBankAreaMapper.selectList(new QueryWrapper<>());

        List<DictionaryBankAreaVO> dictionaryBankAreaVOList = dictionaryBankAreaList.stream().map(dictionaryBankArea -> GeneralConvertorUtil.convertor(dictionaryBankArea,DictionaryBankAreaVO.class)).collect(Collectors.toList());

        log.info("dictionaryBankVOList:{}",dictionaryBankAreaVOList);

        return dictionaryBankAreaVOList;
    }

}



























