package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/18
 * @Description h5支付接口接收参数封装
 */
@ApiModel(value = "h5支付接口接收参数封装")
@Data
public class JsPayRequest {

    /**
     * 固码编码
     */
    @Validator(isNotNull = true, maxLength = 22, regexExpression = RegexUtils.REGEX_NUMBER, description = "固码编码")
    @ApiModelProperty(value = "固码编码", name = "fixedQrCode", required = true)
    private String fixedQrCode;

    /**
     * 支付方式（0：支付宝 1：微信 2：银联
     */
    @Validator(isNotNull = true, maxLength = 1, regexExpression = RegexUtils.REGEX_NUMBER, description = "支付方式（0：支付宝 1：微信 2：银联）")
    @ApiModelProperty(value = "支付方式（0：支付宝 1：微信 2：银联）", name = "payType", required = true)
    private String payType;

    /**
     * 支付金额
     */
    @Validator(isNotNull = true, maxLength = 15, regexExpression = RegexUtils.REGEX_AMT, description = "支付金额")
    @ApiModelProperty(value = "支付金额", name = "amount", required = true)
    private String amount;

    /**
     * 支付宝微信userId
     */
    @Validator(isNotNull = true, description = "userId")
    @ApiModelProperty(value = "微信支付宝userId", name = "userId", required = true)
    private String userId;
}
