package com.cloudbest.order.otherentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CStock implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * sku_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品ID
     */
    @TableId(value = "item_id")
    private Integer itemId;

    /**
     * 商品属性
     */
    @TableId(value = "item_attr")
    private String itemAttr;

    /**
     * 仓库ID
     */
    @TableId(value = "repository_id")
    private Integer repositoryId;

    /**
     * 商品原价
     */
    @TableId(value = "pre_sale_price")
    private BigDecimal preSalePrice;

    /**
     * 商品现价
     */
    @TableId(value = "sale_price")
    private BigDecimal salePrice;

    /**
     * 最大可用购物券比例
     */
    @TableId(value = "score_scale")
    private BigDecimal scoreScale;

    /**
     * 长宽高
     */
    @TableId(value = "len_wid_height")
    private String lenWidHeight;

    /**
     * 重量
     */
    @TableId(value = "weight")
    private double weight;

    /**
     * 状态（0：下架；1：上架；9：逻辑删除）
     */
    @TableId(value = "status")
    private Integer status;

    /**
     * 可用库存数（库存总数-占用数量）
     */
    @TableId(value = "usable_stock")
    private Integer usableStock;


    public Date getGroudingTime() {
        return groudingTime;
    }

    public void setGroudingTime(Date groudingTime) {
        this.groudingTime = groudingTime;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;
    }

    /**
     * 商品上架时间
     */
    @TableId(value = "grouding_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date groudingTime;

    /**
     * 商品下架时间
     */
    @TableId(value = "validity_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityTime;

    /**
     * sku销量
     */
    @TableId(value = "sku_salt_volume")
    private Integer skuSaltVolume;

    /**
     * 最后修改时间
     */
    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(String itemAttr) {
        this.itemAttr = itemAttr;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public BigDecimal getPreSalePrice() {
        return preSalePrice;
    }

    public void setPreSalePrice(BigDecimal preSalePrice) {
        this.preSalePrice = preSalePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getScoreScale() {
        return scoreScale;
    }

    public void setScoreScale(BigDecimal scoreScale) {
        this.scoreScale = scoreScale;
    }

    public String getLenWidHeight() {
        return lenWidHeight;
    }

    public void setLenWidHeight(String lenWidHeight) {
        this.lenWidHeight = lenWidHeight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Integer getUsableStock() {
        return usableStock;
    }

    public void setUsableStock(Integer usableStock) {
        this.usableStock = usableStock;
    }

    public Integer getSkuSaltVolume() {
        return skuSaltVolume;
    }

    public void setSkuSaltVolume(Integer skuSaltVolume) {
        this.skuSaltVolume = skuSaltVolume;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", itemAttr='" + itemAttr + '\'' +
                ", repositoryId=" + repositoryId +
                ", preSalePrice=" + preSalePrice +
                ", salePrice=" + salePrice +
                ", scoreScale=" + scoreScale +
                ", lenWidHeight='" + lenWidHeight + '\'' +
                ", weight=" + weight +
                ", status=" + status +
                ", usableStock=" + usableStock +
                ", groudingTime=" + groudingTime +
                ", validityTime=" + validityTime +
                ", skuSaltVolume=" + skuSaltVolume +
                ", updateTime=" + updateTime +
                '}';
    }
}
