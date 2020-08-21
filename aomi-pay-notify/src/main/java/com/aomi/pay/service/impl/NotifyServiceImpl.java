package com.aomi.pay.service.impl;

import com.aomi.pay.constants.ApiConstants;
import com.aomi.pay.constants.PayConstants;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.mapper.order.PaymentOrderMapper;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.NotifyService;
import com.aomi.pay.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    /**
     * @author hdq
     * @date 2020/8/15
     * @Param NotifyRequest notifyRequest
     * @desc 支付回调
     */
    @Override
    public void payNotify(NotifyRequest notifyRequest) throws Exception {
        //同步订单信息
        PaymentOrder paymentOrder = new PaymentOrder();
        int payStatus = tradeStatusToPayStatus(notifyRequest.getTradeStatus());
        paymentOrder.setPayStatus(payStatus);
        paymentOrder.setCompleteTime(DateUtil.format(notifyRequest.getCompleteTime(), DateUtil.YYYYMMDDHHMMSS));
        paymentOrder.setOutTransactionId(notifyRequest.getOutTransactionId());
        paymentOrder.setOrderId(new BigInteger(notifyRequest.getOutTradeNo()));

        paymentOrderMapper.updateById(paymentOrder);

        //TODO 调通知
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 平台支付状态转换系统支付状态
     **/
    private int tradeStatusToPayStatus(String tradeStatus) {
        int payStatus = 0;
        switch (tradeStatus) {
            case ApiConstants.TRADE_STATUS_SUCCESS:
                payStatus = PayConstants.PAY_STATUS_SUCCESS;
                break;
            case ApiConstants.TRADE_STATUS_PROCESSING:
                payStatus = PayConstants.PAY_STATUS_TO_BE_PAY;
                break;
            case ApiConstants.TRADE_STATUS_FAIL:
                payStatus = PayConstants.PAY_STATUS_FAIL;
                break;
            case ApiConstants.TRADE_STATUS_CLOSE:
                payStatus = PayConstants.PAY_STATUS_CLOSE;
                break;
            case ApiConstants.TRADE_STATUS_REFUND:
                payStatus = PayConstants.PAY_STATUS_REFUND;
                break;
            default:
                //默认待支付
                payStatus = PayConstants.PAY_STATUS_TO_BE_PAY;
        }
        return payStatus;
    }

}



























