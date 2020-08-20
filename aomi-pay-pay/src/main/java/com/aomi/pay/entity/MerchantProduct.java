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

/**
 * <p>
 * 商户产品利率表
 * </p>
 *
 * @author hdq
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
@TableName("t_merchant_product")
public class MerchantProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 产品编码值
     */
    @TableField("product_code")
    private String productCode;
    /**
     * 签约费率id值
     */
    @TableField("model_id")
    private String modelId;
    /**
     * 产品描述
     */
    private String note;
    /**
     * 平台标识(hx)
     */
    @TableField("platform_tag")
    private String platformTag;

    /**
     * 支付方式  0 支付宝 1微信 2银联
     */
    @TableField("pay_type")
    private Integer payType;
}
