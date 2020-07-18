package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 限购规则实体类
 *
 * @author : hdq
 * @date 2020/7/18 11:44
 */
@Data
@TableName("c_purchase_limit")
public class PurchaseLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @TableId(value = "item_id")
    private Integer itemId;

    /**
     * skuId
     */
    @TableId(value = "sku_id")
    private Integer skuId;

    /**
     * 限购频率（天）
     */
    @TableId(value = "purchase_limit_frequency")
    private Integer purchaseLimitFrequency;

    /**
     * 限购数量
     */
    @TableId(value = "purchase_limit_volume")
    private Integer purchaseLimitVolume;


    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
