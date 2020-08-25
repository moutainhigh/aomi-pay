package com.aomi.pay.service.impl;

import com.aomi.pay.config.DbContextHolder;
import com.aomi.pay.constants.PayConstants;
import com.aomi.pay.entity.MerchantAudioBind;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.enums.DBTypeEnum;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.service.NotifyBizService;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.NotifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Slf4j
@Service
public class NotifyBizServiceImpl implements NotifyBizService {

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private MerchantService merchantService;

    /**
     * @param notifyRequest 接收参数
     * @author hdq
     * @date 2020/8/8
     * @desc 异步通知回调业务处理
     */
    @Override
    public String payNotify(NotifyRequest notifyRequest) throws Exception {
        //手动切数据源
        DbContextHolder.setDbType(DBTypeEnum.order);
        //查询订单信息
        PaymentOrder paymentOrder = paymentOrderService.queryByOrderId(new BigInteger(notifyRequest.getOutTradeNo()));
        log.info("订单信息:{}", paymentOrder);
        //判断是否已经同步过信息 已经同步则不再修改状态
        if (paymentOrder.getPayStatus() == PayConstants.PAY_STATUS_SUCCESS) {
            log.info("进了没");
            return "SUCCESS";
        }
        //同步订单信息
        paymentOrderService.updateOrder(notifyRequest);

        //通知响应支付成功
        if (notifyRequest.getTradeStatus().equals(PayConstants.HX_PAY_STATUS_SUCCESS)) {
            try {
                DbContextHolder.setDbType(DBTypeEnum.user);
                List<MerchantAudioBind> merchantAudioBinds = merchantService.queryAudiosByMerchantId(paymentOrder.getMerchantId());
                if (!merchantAudioBinds.isEmpty()) {
                    for (MerchantAudioBind merchantAudioBind : merchantAudioBinds) {
                        NotifyUtil.send(merchantAudioBind.getAudioCode(), merchantAudioBind.getAudioType(), paymentOrder.getPayType(), paymentOrder.getAmount());
                    }
                }
            } catch (Exception e) {
                log.error("播报异常:", e);
                return "SUCCESS";
            }
        }
        log.info("--------支付回调结束--------");
        return "SUCCESS";
    }
}



























