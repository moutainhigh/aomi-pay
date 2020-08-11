package com.aomi.pay.service;

import com.aomi.pay.vo.DictionaryBankVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行区域编码字典Service
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Transactional(rollbackFor = Exception.class)
public interface DictionaryBankService {

    /**
     * 获取所有银行区域编码信息
     */
    List<DictionaryBankVO> getAllBankArea();

}
