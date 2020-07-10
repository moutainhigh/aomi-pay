package com.cloudbest.items.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemsStockVO implements Serializable {



    private static final long serialVersionUID = 1L;

    /**
     * sku_id
     */
    private Integer id;

    /**
     * sku_id
     */
    private Integer skuId;

    /**
     * 商品ID
     */
    private Integer itemId;

    /**
     * 商品属性
     */
    private String itemAttr;

    /**
     * 仓库ID
     */
    private Integer repositoryId;

    /**
     * 商品原价
     */
    private BigDecimal preSalePrice;

    /**
     * 商品现价
     */
    private BigDecimal salePrice;

    /**
     * 最大可用购物券比例
     */
    private BigDecimal scoreScale;

    /**
     * 可用购物券
     */
    private BigDecimal score;

    /**
     * 购物券抵换后所需金额
     */
    private BigDecimal score_price;

    /**
     * 长宽高
     */
    private String lenWidHeight;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 可用库存数（库存总数-占用数量）
     */
    private Integer usableStock;

    private Integer status;

    /**
     * sku销量
     */
    private Integer skuSaltVolume;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    private String imgUrl;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLenWidHeight() {
        return lenWidHeight;
    }

    public void setLenWidHeight(String lenWidHeight) {
        this.lenWidHeight = lenWidHeight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScore_price() {
        return score_price;
    }

    public void setScore_price(BigDecimal score_price) {
        this.score_price = score_price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "ItemsStockVO{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", itemId=" + itemId +
                ", itemAttr='" + itemAttr + '\'' +
                ", repositoryId=" + repositoryId +
                ", preSalePrice=" + preSalePrice +
                ", salePrice=" + salePrice +
                ", scoreScale=" + scoreScale +
                ", score=" + score +
                ", score_price=" + score_price +
                ", lenWidHeight='" + lenWidHeight + '\'' +
                ", weight=" + weight +
                ", usableStock=" + usableStock +
                ", status=" + status +
                ", skuSaltVolume=" + skuSaltVolume +
                ", updateTime=" + updateTime +
                ", imgUrl='" + imgUrl + '\'' +
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}
