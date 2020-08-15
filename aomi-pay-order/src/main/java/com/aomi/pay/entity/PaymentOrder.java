package com.aomi.pay.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 交易订单表
 * </p>
 *
 * @author hdq
 * @since 2020-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
@TableName("t_payment_order")
public class PaymentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId(value = "order_id", type = IdType.INPUT)
    private BigInteger orderId;

    /**
     * 平台订单号
     */
    @TableField("platform_order_id")
    private String platformOrderId;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 商户号
     */
    @TableField("merchant_id")
    private String merchantId;
    /**
     * 平台商户号
     */
    @TableField("platform_merchant_id")
    private String platformMerchantId;
    /**
     * 费率
     */
    private BigDecimal rate;
    /**
     * 代理商id
     */
    @TableField("agent_id")
    private Long agentId;
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
    @TableField("settle_type")
    private Integer settleType;

    /**
     * 支付方式  0 支付宝 1微信 2银联
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 外部流水号 支付宝微信交易流水号
     */
    @TableField("out_transaction_id")
    private String outTransactionId;
    /**
     * 通知地址
     */
    @TableField("notify_url")
    private String notifyUrl;
    /**
     * 0  待支付   1已成功    2失败   3已关闭 4已退款
     */
    @TableField("pay_status")
    private Integer payStatus;
    /**
     * 卡号
     */
    @TableField("bank_card")
    private String bankCard;
    /**
     * 交易有效期（分钟）
     */
    private Integer exprice;
    /**
     * 原生支付信息
     */
    @TableField("pay_info")
    private String payInfo;

    /**
     * 平台标识(hx)
     */
    @TableField("platform_tag")
    private String platformTag;

    /**
     * 订单创建日期
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 支付完成时间
     */
    @TableField("complete_time")
    private Date completeTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 订单创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
