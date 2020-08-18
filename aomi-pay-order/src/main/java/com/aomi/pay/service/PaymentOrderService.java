package com.aomi.pay.service;

import com.aomi.pay.domain.PageResult;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.model.QueryListResponse;
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
     * @Param merchantId 系统商户id，amount 金额，payType 支付类型（微信，支付宝，银联 编号）
     */
    String jsPay(JsPayRequest req) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 支付回调
     **/
    void payNotify(NotifyRequest notifyRequest) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/18
     * @desc 根据商户id查询交易记录列表  (分页)
     **/
    PageResult queryListForPage(PaymentOrder paymentOrder, Integer pageNo, Integer pageSize);

}
