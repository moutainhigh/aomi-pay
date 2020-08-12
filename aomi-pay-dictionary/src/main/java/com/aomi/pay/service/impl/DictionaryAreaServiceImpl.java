package com.aomi.pay.service.impl;

import com.aomi.pay.entity.DictionaryArea;
import com.aomi.pay.mapper.DictionaryAreaMapper;
import com.aomi.pay.service.DictionaryAreaService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.vo.DictionaryAreaVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 区域编码数据字典Service实现类
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryAreaServiceImpl implements DictionaryAreaService {

    @Resource
    private DictionaryAreaMapper dictionaryAreaMapper;

    /**
     * 获取所有区域编码信息
     */
    @Cacheable(value = "bankAreaCache", key = "'all'")
    @Override
    public List<DictionaryAreaVO> getAllArea() {
        //获取所有的区域编码信息
        List<DictionaryArea> dictionaryAreaList = dictionaryAreaMapper.selectList(new QueryWrapper<>());

        List<DictionaryAreaVO> dictionaryAreaVOList = dictionaryAreaList.stream().map(dictionaryArea -> GeneralConvertorUtil.convertor(dictionaryArea,DictionaryAreaVO.class)).collect(Collectors.toList());

        log.info("dictionaryVOList:{}",dictionaryAreaVOList);

        return dictionaryAreaVOList;
    }

}



























