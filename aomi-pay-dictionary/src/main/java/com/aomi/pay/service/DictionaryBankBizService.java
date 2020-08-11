package com.aomi.pay.service;

import com.aomi.pay.vo.DictionaryBankVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行编码数据字典Service
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Transactional(rollbackFor = Exception.class)
public interface DictionaryBankBizService {

    /**
     * 根据关键字模糊匹配银行信息
     */
    List<DictionaryBankVO> getBankByKeywords(String keywords) throws Exception;

}
