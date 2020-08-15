package com.aomi.pay.service;

import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * 交易Service
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Transactional(rollbackFor = Exception.class)
public interface PaymentOrderService {

    /**
     * h5支付  商户id，金额，支付类型
     *
     * @return 原生js信息
     * @Param merchantId 系统商户id，amount 金额，payType 支付类型（微信，支付宝，银联 编号）
     */
    String jsPay(JsPayRequest req) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 支付回调
     **/
    void payNotify(NotifyRequest notifyRequest) throws Exception;

}
