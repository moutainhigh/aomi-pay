package com.aomi.pay.service.impl;

import com.aomi.pay.service.DictionaryBankBizService;
import com.aomi.pay.service.DictionaryBankService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.JsonUtil;
import com.aomi.pay.util.RedisUtil;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.DictionaryBankVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class DictionaryBankBizServiceImpl implements DictionaryBankBizService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private DictionaryBankService dictionaryBankService;

    /**
     * 根据关键字模糊匹配银行信息
     */
    @Override
    public List<DictionaryBankVO> getBankByKeywords(String keywords) throws Exception {
        //关键字为空不返回数据
        if(StringUtil.isBlank(keywords)){
            return null;
        }
        //从redis获取所有的银行编码信息
        String result = redisUtil.getString("bankCache::all");
        List<DictionaryBankVO> dictionaryBankVOS;
        if (StringUtil.isNotBlank(result)) {
            List<Map<String, Object>> list = JsonUtil.jsonToList(result);
            dictionaryBankVOS = list.stream().map(obj -> GeneralConvertorUtil.convertor(obj, DictionaryBankVO.class)).collect(Collectors.toList());
        } else {//没取到就从数据库取，再存redis
            dictionaryBankVOS = dictionaryBankService.getAllBank();
        }

        List<DictionaryBankVO> dictionaryBankVOList = GeneralConvertorUtil.fuzzyQuery(dictionaryBankVOS,keywords,"getBankName");

        return dictionaryBankVOList;
    }
}



























