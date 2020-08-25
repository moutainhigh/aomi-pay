package com.aomi.pay.service;

import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.model.NotifyRequest;

import java.math.BigInteger;

/**
 * 交易Service
 *
 * @author : hdq
 * @date 2020/8/7
 */
public interface PaymentOrderService {


    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 更新订单信息
     * @return paytype 支付类型
     **/
    void updateOrder(NotifyRequest notifyRequest) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/24
     * @desc 根据订单号查订单信息
     **/
    PaymentOrder queryByOrderId(BigInteger orderId);
}
