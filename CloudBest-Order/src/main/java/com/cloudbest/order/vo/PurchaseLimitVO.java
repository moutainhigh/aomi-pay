package com.cloudbest.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PurchaseLimitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * skuId
     */
    private Integer skuId;

    /**
     * 限购频率（天）
     */
    private Integer purchaseLimitFrequency;

    /**
     * 限购数量
     */
    private Integer purchaseLimitVolume;

    /**
     * 更新时间
     */
    private Date updateTime;

}
