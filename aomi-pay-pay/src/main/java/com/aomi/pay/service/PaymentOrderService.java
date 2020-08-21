package com.aomi.pay.service;

import com.aomi.pay.vo.JsPayVO;
import org.springframework.transaction.annotation.Transactional;


/**
 * 交易Service
 *
 * @author : hdq
 * @date 2020/8/18
 */
@Transactional(rollbackFor = Exception.class)
public interface PaymentOrderService {

    /**
     * h5支付  商户id，金额，支付类型
     *
     * @return 原生js信息
     */
    String jsPay(JsPayVO jsPayVO) throws Exception;

}
