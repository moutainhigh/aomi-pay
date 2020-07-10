package com.cloudbest.payment.service;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.payment.dto.AlipayBean;
import com.cloudbest.payment.dto.PaymentResponseDTO;

public interface PaymentService {

    /**
     * 调用支付宝的下单接口
     *
     * @param alipayBean 业务参数（商户订单号，订单标题，订单描述,,）
     * @return 统一返回PaymentResponseDTO
     */
    public String createPayOrderByAliWAP( AlipayBean alipayBean) throws BusinessException;

    /**
     * 查询支付宝订单状态
     *
     * @param outTradeNo 平台的订单号
     * @return
     */
    public PaymentResponseDTO queryPayOrderByAli(String outTradeNo) throws BusinessException;
}
