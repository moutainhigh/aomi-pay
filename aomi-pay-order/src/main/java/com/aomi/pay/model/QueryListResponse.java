package com.aomi.pay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author hdq
 * @Date 2020/8/18
 * @Description h5支付接口接收参数封装
 */
@ApiModel(value = "h5支付接口接收参数封装")
@Data
public class QueryListResponse {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号", name = "orderId")
    private String orderId;

    /**
     * 支付方式（0：支付宝 1：微信 2：银联
     */
    @ApiModelProperty(value = "支付方式（0：支付宝 1：微信 2：银联）", name = "payType")
    private String payType;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额", name = "amount")
    private String amount;

    /**
     * 结算周期 结算周期
     */
    @ApiModelProperty(value = "结算周期", name = "settleType")
    private String settleType;

    /**
     * 结算周期 结算周期
     */
    @ApiModelProperty(value = "结算周期", name = "completeTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;
}
