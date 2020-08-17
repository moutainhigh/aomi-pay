package com.aomi.pay.service.impl;

import com.aomi.pay.constants.ApiConstants;
import com.aomi.pay.constants.PayConstants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.dto.hx.JsPayDTO;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.enums.PayEnums;
import com.aomi.pay.feign.ApiClient;
import com.aomi.pay.mapper.PaymentOrderMapper;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.*;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
@RefreshScope
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    @Resource
    private ApiClient apiClient;

    /**
     * 支付回调地址
     */
    @Value("${pay.hx.notify_url}")
    private String notifyUrl;

    /**
     * 结算周期
     */
    @Value("${pay.hx.settle_type}")
    private int settleType;

    /**
     * 订单失效时间
     */
    @Value("${pay.hx.exprice}")
    private int exprice;

    /**
     * 微信appid
     */
    @Value("${wx.appid}")
    private String appid;

    /**
     * h5支付 商户id，金额，支付类型
     *
     * @return 原生js信息
     */
    @Override
    public String jsPay(JsPayRequest req) throws Exception {

        //根据商户号获取需要的商户信息 TODO 暂时写死  redis req.getMerchantId()
        Long merchantId = 135795456L;
        //平台商户号
        String platformMerchantId = "027310103367232";
        //TODO  商户名+收款
        String subject = "全季酒店(川沙店) 收款";
        //String merchantNo = "10000000005";//机构商户号
        String bdNo = "1000000001";
        //生成交易订单号
        BigInteger orderId = PaymentOrderUtil.getOrderCode();
        int payType = Integer.parseInt(req.getPayType());
        JsPayDTO jsPayDTO = new JsPayDTO();
        jsPayDTO.setOrderId(orderId);
        jsPayDTO.setAmount(new BigDecimal(req.getAmount()));
        jsPayDTO.setPayType(payType);
        //产品费率编码  //TODO 暂时先写死  查商户开通了哪些产品 根据支付类型选择  取出redis 或者 查询环迅平台接口
        if (payType == PayConstants.PAY_TYPE_ZFB) {
            jsPayDTO.setProductCode(100043);
            jsPayDTO.setUserId(req.getUserId());
        } else if (payType == PayConstants.PAY_TYPE_WX) {
            jsPayDTO.setProductCode(100042);
            //微信需要传subAppid
            jsPayDTO.setSubAppid(appid);
            jsPayDTO.setUserId(req.getUserId());
        } else if (payType == PayConstants.PAY_TYPE_YL) {
            jsPayDTO.setProductCode(100044);
        }
        jsPayDTO.setExprice(exprice);
        //平台商户号
        jsPayDTO.setPlatformMerchantId(platformMerchantId);
        jsPayDTO.setNotifyUrl(notifyUrl);
        //TODO 结算周期  暂D0 如果失败重新下单 T1
        jsPayDTO.setSettleType(PayEnums.SETTLE_TYPE_T1);
        jsPayDTO.setSubject(subject);
        //调用环迅api接口
        BaseResponse response = apiClient.onlineTrade(jsPayDTO);
        log.info("环迅h5-api：{}", response);
        if (!CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
            CommonExceptionUtils.throwBusinessException(response.getCode(), response.getMessage());
        }
        JSONObject jsonObject = JSONObject.fromObject(response.getData());
        //实体转化
        PaymentOrder paymentOrder = GeneralConvertorUtil.convertor(jsPayDTO, PaymentOrder.class);
        //商户号 TODO 暂写死
        paymentOrder.setMerchantId(merchantId);
        paymentOrder.setBdNo(bdNo);
        //paymentOrder.setCreateDate(DateUtil.format(DateUtil.getCurrDate(), DateUtil.YYYY_MM_DD));
        paymentOrder.setCreateDate(DateUtil.getCurrDate());
        //TODO 写死环迅标识
        paymentOrder.setPlatformTag("hx");
        paymentOrder = objectToPaymenOrder(paymentOrder, jsonObject);
        log.info("paymentOrder:{}", paymentOrder);
        //插入订单交易记录
        paymentOrderMapper.insert(paymentOrder);

        return JSONObject.fromObject(paymentOrder.getPayInfo()).toString();
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 实体转化
     **/
    private PaymentOrder objectToPaymenOrder(PaymentOrder paymentOrder, JSONObject jsonObject) {
        //平台商户号
        paymentOrder.setPlatformMerchantId(jsonObject.getString(ApiConstants.MERCHANT_NO_NAME));
        if (jsonObject.has(ApiConstants.TEADE_NO_NAME) && StringUtil.isNotBlank(jsonObject.getString(ApiConstants.TEADE_NO_NAME))) {
            //平台订单号
            paymentOrder.setPlatformOrderId(jsonObject.getString(ApiConstants.TEADE_NO_NAME));
        }
        //平台支付状态转换系统支付状态
        if (jsonObject.has(ApiConstants.TRADE_STATUS_NAME)) {
            paymentOrder.setPayStatus(tradeStatusToPayStatus(jsonObject.getString(ApiConstants.TRADE_STATUS_NAME)));
        }
        if (jsonObject.has(ApiConstants.TRADE_TIME_NAME)) {
            paymentOrder.setCreateTime(DateUtil.format(jsonObject.getString(ApiConstants.TRADE_TIME_NAME), DateUtil.YYYYMMDDHHMMSS));
        }
        if (jsonObject.has(ApiConstants.PAY_INFO_NAME)) {
            paymentOrder.setPayInfo(jsonObject.getString(ApiConstants.PAY_INFO_NAME));
        }
        return paymentOrder;
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 平台支付状态转换系统支付状态
     **/
    private int tradeStatusToPayStatus(String tradeStatus) {
        int payStatus = PayConstants.PAY_STATUS_TO_BE_PAY;//默认待支付
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
        }
        return payStatus;
    }

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
        paymentOrder.setCompleteTime(DateUtil.format(notifyRequest.getCompleteTime(),DateUtil.YYYYMMDDHHMMSS));
        paymentOrder.setOutTransactionId(notifyRequest.getOutTransactionId());
        paymentOrder.setOrderId(new BigInteger(notifyRequest.getOutTradeNo()));
        paymentOrderMapper.updateByOrderId(paymentOrder);
    }

}



























