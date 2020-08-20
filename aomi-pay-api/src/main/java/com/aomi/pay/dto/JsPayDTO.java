package com.aomi.pay.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * h5支付传递到api服务的参数dto
 *
 * @author hdq
 * @since 2020-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class JsPayDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Mapping("outTradeNo")
    private BigInteger orderId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 平台商户号
     */
    @Mapping("merchantNo")
    private String platformMerchantId;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 结算周期 默认0：T+1  1：T+0  2：S+0 3：D+0 4：D+1
     */
    private Integer settleType;

    /**
     * 支付宝微信userId
     */
    private String userId;

    /**
     * 子商户微信公众号appid
     */
    private String subAppid;

    /**
     * 支付方式  0支付宝 1微信 2银联
     */
    private Integer payType;

    /**
     * 外部流水号 支付宝微信交易流水号
     */
    private String outTransactionId;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 0  待支付   1已成功    2失败   3已关闭 4已退款
     */
    private String payStatus;

    /**
     * 交易有效期（分钟）
     */
    private Integer exprice;

    /**
     * 产品编码
     */
    private String productCode;

}
