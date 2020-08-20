package com.aomi.pay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品利率
 *
 * @author hdq
 * @since 2020-08-18
 */
@Data
@ApiModel(value = "产品利率返回参数封装")
public class ProductResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 产品编码值
     */
    @ApiModelProperty(value = "产品编码值", name = "productCode")
    private String productCode;

    /**
     * 签约费率id值
     */
    @ApiModelProperty(value = "签约费率id值", name = "modelId")
    private String modelId;

    /**
     * 支付方式  0支付宝 1微信 2银联
     */
    @ApiModelProperty(value = "支付方式  0支付宝 1微信 2银联", name = "payType")
    private Integer payType;
}
