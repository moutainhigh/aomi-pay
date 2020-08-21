package com.aomi.pay.service;

import com.aomi.pay.model.NotifyRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易Service
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Transactional(rollbackFor = Exception.class)
public interface NotifyService {


    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 支付回调
     **/
    void payNotify(NotifyRequest notifyRequest) throws Exception;

}
