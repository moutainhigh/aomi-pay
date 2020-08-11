package com.aomi.pay.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "订单号", name = "orderId", required = true)
    private BigInteger orderId;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额", name = "amount", required = true)
    private BigDecimal amount;

    /**
     * 平台商户号
     */
    @Mapping("merchantNo")
    @ApiModelProperty(value = "平台商户号", name = "platformMerchantId", required = true)
    private String platformMerchantId;

    /**
     * 订单标题
     */
    @ApiModelProperty(value = "订单标题", name = "subject", required = true)
    private String subject;

    /**
     * 附加数据
     */
    @ApiModelProperty(value = "附加数据", name = "attach")
    private String attach;

    /**
     * 结算周期 默认0：T+1  1：T+0  2：S+0 3：D+0 4：D+1
     */
    @ApiModelProperty(value = "结算周期 默认0：T+1  1：T+0  2：S+0 3：D+0 4：D+1", name = "settleType", required = true)
    private Integer settleType;

    /**
     * 支付宝微信userId
     */
    @ApiModelProperty(value = "支付宝微信userId", name = "userId", required = true)
    private String userId;

    /**
     * 子商户微信公众号appid
     */
    @ApiModelProperty(value = "子商户微信公众号appid", name = "subAppid")
    private String subAppid;

    /**
     * 支付方式  0支付宝 1微信 2银联
     */
    @ApiModelProperty(value = "支付方式  0支付宝 1微信 2银联", name = "payType", required = true)
    private Integer payType;

    /**
     * 外部流水号 支付宝微信交易流水号
     */
    @ApiModelProperty(value = "支付宝微信交易流水号", name = "payType")
    private String outTransactionId;

    /**
     * 通知地址
     */
    @ApiModelProperty(value = "通知地址", name = "notifyUrl", required = true)
    private String notifyUrl;

    /**
     * 交易状态  0  待支付   1已成功    2失败   3已关闭 4已退款
     */
    @ApiModelProperty(value = "交易状态   0  待支付   1已成功    2失败   3已关闭 4已退款", name = "payStatus")
    private String payStatus;

    /**
     * 交易有效期（分钟）
     */
    @ApiModelProperty(value = "交易有效期（分钟）", name = "exprice", required = true)
    private Integer exprice;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码", name = "productCode", required = true)
    private Integer productCode;

}
