package com.aomi.pay.service.impl;

import com.aomi.pay.entity.DictionaryBank;
import com.aomi.pay.mapper.DictionaryBankMapper;
import com.aomi.pay.service.DictionaryBankService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.vo.DictionaryBankVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 银行编码数据字典Service实现类
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryBankServiceImpl implements DictionaryBankService {

    @Resource
    private DictionaryBankMapper dictionaryBankMapper;

    /**
     * 获取所有银行编码信息
     */
    @Cacheable(value = "bankCache", key = "'all'")
    @Override
    public List<DictionaryBankVO> getAllBank() {
        //获取所有的银行编码信息
        List<DictionaryBank> dictionaryBankList = dictionaryBankMapper.selectList(new QueryWrapper<>());

        List<DictionaryBankVO> dictionaryBankVOList = dictionaryBankList.stream().map(dictionaryBank -> GeneralConvertorUtil.convertor(dictionaryBank,DictionaryBankVO.class)).collect(Collectors.toList());

        log.info("dictionaryBankVOList:{}",dictionaryBankVOList);

        return dictionaryBankVOList;
    }

}



























