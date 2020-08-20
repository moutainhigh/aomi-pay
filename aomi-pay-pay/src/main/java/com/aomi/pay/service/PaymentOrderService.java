package com.aomi.pay.service;

import com.aomi.pay.domain.PageResult;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.model.QueryListResponse;
import com.aomi.pay.vo.JsPayVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
     */
    String jsPay(JsPayVO jsPayVO) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 支付回调
     **/
    void payNotify(NotifyRequest notifyRequest) throws Exception;

}
