package com.aomi.pay.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商户产品利率绑定关系表
 * </p>
 *
 * @author author
 * @since 2020-08-11
 */
@Data
@TableName("t_merchant_product_bind")
public class MerchantProductBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机构商户id
     */
    private String merchantId;

    /**
     * 产品id
     */
    private Integer productId;


}
