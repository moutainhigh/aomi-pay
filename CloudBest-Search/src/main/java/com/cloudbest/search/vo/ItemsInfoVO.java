package com.cloudbest.search.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 搜索中的商品信息VO
 *
 * @author : hdq
 * @date 2020/7/10 11:31
 */
@Data
public class ItemsInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer spuId;
    private String name;
    private String imgDefault;

    /**
     * 一级分类ID
     */
    private Integer firstCategoryId;

    /**
     * 二级分类ID
     */
    private Integer secondCategoryId;

    /**
     * 三级分类ID
     */
    private Integer thirdCategoryId;


    private Integer isView;


    private String description;

    private BigDecimal preSalePrice;
    private BigDecimal salePrice;
    private BigDecimal score;
    private BigDecimal scorePrice;
    private BigDecimal transportAmount;
    /**
     * 商品详情
     */
    private String itemInfo;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

}
