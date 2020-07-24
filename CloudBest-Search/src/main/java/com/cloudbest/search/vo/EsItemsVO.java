package com.cloudbest.search.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 搜索中的商品信息
 *
 * @author : hdq
 * @date 2020/7/10 11:31
 */
@Data
@ApiModel
public class EsItemsVO implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id", name = "spuid")
    private Integer id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", name = "name")
    private String name;
    /**
     * 一级分类id
     */
    @ApiModelProperty(value = "一级分类id", name = "firstCategoryId")
    private Integer firstCategoryId;
    /**
     * 一级分类名称
     */
    @ApiModelProperty(value = "一级分类名称", name = "firstCategoryName")
    private String firstCategoryName;
    /**
     * 二级分类id
     */
    @ApiModelProperty(value = "二级分类id", name = "secondCategoryId")
    private Integer secondCategoryId;
    /**
     * 二级分类名称
     */
    @ApiModelProperty(value = "二级分类名称", name = "secondCategoryName")
    private String secondCategoryName;
    /**
     * spu销量
     */
    @ApiModelProperty(value = "spu销量", name = "spuSaltVolume")
    private Integer spuSaltVolume;
    /**
     * 上架时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上架时间", name = "groudingTime")
    private Date groudingTime;
    /**
     * 下架时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下架时间", name = "validityTime")
    private Date validityTime;
    /**
     * 商品折后价格   保留进度输出自动转化json
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "商品折后价格", name = "discountedPrice")
    private BigDecimal discountedPrice;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述", name = "description")
    private String description;

}
