package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
@Document(indexName = "c_items_info", type = "itemsInfo", shards = 1, replicas = 0)
@TableName("c_items_info")
public class EsItems implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * 商品id
     */
    @Id
    private Integer id;
    /**
     * 商品名称
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String name;
    /**
     * 一级分类id
     */
    private Integer firstCategoryId;
    /**
     * 二级分类id
     */
    private Integer secondCategoryId;
    /**
     * 一级分类名称
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String firstCategoryName;
    /**
     * 二级分类名称
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String secondCategoryName;
    /**
     * spu销量
     */
    private Integer spuSaltVolume;
    /**
     * 上架时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date groudingTime;
    /**
     * 下架时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityTime;
    /**
     * 商品现价
     */
    private BigDecimal discountedPrice;

    /**
     * 商品描述
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String description;

}