package com.aomi.pay.service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
     * @date 2020/8/15
     * @desc 获取支付宝userid
     **/
    void getUserId(String fixedQrCode,HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/15
     * @desc 获取微信
     **/
    void getOpenId(String fixedQrCode,HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception;
}
