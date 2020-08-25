package com.aomi.pay.service;

import com.aomi.pay.model.NotifyRequest;
import org.springframework.transaction.annotation.Transactional;


/**
 * 回调Service
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Transactional(rollbackFor = Exception.class)
public interface NotifyBizService {


    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 更新订单信息
     * @return paytype 支付类型
     **/
    String payNotify(NotifyRequest notifyRequest) throws Exception;

}
