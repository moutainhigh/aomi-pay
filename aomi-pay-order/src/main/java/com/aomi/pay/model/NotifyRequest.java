package com.aomi.pay.model;

import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/7
 * @Description 环迅异步通知接口接收参数封装
 */
@Data
public class NotifyRequest {

    /**
     * 合作方机构号
     */
    private String isvOrgId;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 子商户微信公众号appid
     */
    private String subAppid;

    /**
     * userId
     */
    private String userId;

    /**
     * 用户是否关注子商户公众号
     */
    private String subIsSubscribe;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    /**
     * 外部流水号
     */
    private String outTradeNo;

    /**
     * 平台流水号
     */
    private String tradeNo;

    /**
     * 微信支付宝订单号
     */
    private String outTransactionId;

    /**
     * 外部商户编号  银联：银联商户编号
     */
    private String outMerchantNo;

    /**
     * 外部终端号
     */
    private String outTermNo;

    /**
     * 支付完成时间
     */
    private String completeTime;

    /**
     * 交易类型 （31 消费  退款 35）
     */
    private String tradeType;

}
