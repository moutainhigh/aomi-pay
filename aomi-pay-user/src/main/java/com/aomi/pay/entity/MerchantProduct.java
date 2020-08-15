package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
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
    private String productCode;

    /**
     * 签约费率id值
     */
    private String modelId;

    /**
     * 产品描述
     */
    private String note;

    /**
     * 平台标识(hx)
     */
    private String platformTag;


}

