package com.cloudbest.search.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
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
    private String firstCategoryName;
    /**
     * 二级分类名称
     */
    private String secondCategoryName;
    /**
     * spu销量
     */
    private Integer spuSaltVolume;
    /**
     * 上架时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date groudingTime;
    /**
     * 下架时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityTime;
    /**
     * skuid
     */
    private Integer skuId;
    /**
     * 商品属性
     */
    private String itemAttr;
    /**
     * sku销量
     */
    private Integer skuSaltVolume;
    /**
     * 商品现价
     */
    private BigDecimal salePrice;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String keywords;
    /**
     * 商品标题
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String subTitle;
    /*@Field(type =FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;*/
    /**
     * 商品描述
     */
    private String description;

}
