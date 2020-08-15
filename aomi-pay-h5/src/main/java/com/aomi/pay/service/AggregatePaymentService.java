package com.aomi.pay.service;

import org.springframework.transaction.annotation.Transactional;


/**
 * 支付宝Service
 *
 * @author : hdq
 * @date 2020/8/13
 */
@Transactional(rollbackFor = Exception.class)
public interface AggregatePaymentService {

    /**
     * @author hdq
     * @date 2020/8/14
     * @desc 获取支付宝userid
     **/
    String getUserId(String authCode) throws Exception;

}
