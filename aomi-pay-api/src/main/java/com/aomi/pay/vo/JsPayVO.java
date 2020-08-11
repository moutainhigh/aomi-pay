package com.aomi.pay.vo;

import lombok.Data;

/**
 * @Author
 * @Date 2020/8/7 19:26
 * @Version 1.0
 * @Desc 调用环迅h5接口 实体
 */
@Data
public class JsPayVO {

    /**
     * 机构应用id
     */
    private String isvOrgId;
    /**
     * 支付金额
     */
    private String amount;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 机构订单号
     */
    private String outTradeNo;
    /**
     * 通知地址
     */
    private String notifyUrl;
    /**
     * 接口名称
     */
    private String serviceId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 支付宝，微信 appid
     */
    private String userId;
    /**
     * 微信子商户appid
     */
    private String subAppid;
    /**
     * 结算周期
     */
    private String settleType;
    /**
     * 平台商户号
     */
    private String merchantNo;
    /**
     * 交易有效期（分钟）
     */
    private Integer exprice;

}
