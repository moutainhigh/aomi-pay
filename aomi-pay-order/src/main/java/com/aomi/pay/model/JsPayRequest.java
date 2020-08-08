package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/7
 * @Description h5支付接口接收参数封装
 */
@ApiModel(value = "h5支付接口接收参数封装")
@Data
public class JsPayRequest {

    /**
     * 商户id
     */
    @Validator(isNotNull = true, maxLength = 20, regexExpression = RegexUtils.REGEX_NUMBER, description = "商品id")
    @ApiModelProperty(value = "商户id", name = "id", required = true)
    private String id;

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

}
