package com.aomi.pay.vo;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.model.ProductResponse;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author hdq
 * @Date 2020/8/7
 * @Description h5支付接口接收参数封装
 */
@ApiModel(value = "h5支付接口接收参数封装")
@Data
public class JsPayVO {

    /**
     * 支付方式（0：支付宝 1：微信 2：银联
     */
    private int payType;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付宝微信userId
     */
    private String userId;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 平台生成的商户号
     */
    private String platformId;

    /**
     * 商户简称
     */
    private String simpleName;

    /**
     * 推广员id
     */
    private String bdNo;

    /**
     * 产品利率列表
     */
    private List<ProductResponse> productList;
}
