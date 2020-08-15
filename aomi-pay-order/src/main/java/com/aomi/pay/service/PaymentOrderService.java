package com.aomi.pay.service;

import com.aomi.pay.model.JsPayRequest;
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
     * @Param merchantId 系统商户id，amount 金额，payType 支付类型（微信，支付宝，银联 编号）
     * @return 原生js信息
     */
    String jsPay(JsPayRequest req) throws Exception;

    /**
     * @author  hdq
     * @date  2020/8/8
     * @Param tradeStatus 支付状态， platformMerchantId 平台商户号  ， tradeNo 平台流水号，  outTradeNo系统交易id
     * @desc  支付回调
     **/
    String payNotify(String tradeStatus,String platformMerchantId,String tradeNo,String outTradeNo) throws Exception;

}
