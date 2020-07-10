package com.cloudbest.order.otherentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CStock{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", itemAttr='" + itemAttr + '\'' +
                ", repositoryId=" + repositoryId +
                ", preSalePrice=" + preSalePrice +
                ", salePrice=" + salePrice +
                ", lenWidHeight='" + lenWidHeight + '\'' +
                ", weight=" + weight +
                ", scoreScale=" + scoreScale +
                ", city='" + city + '\'' +
                ", usableStock=" + usableStock +
                ", skuSaltVolume=" + skuSaltVolume +
                '}';
    }

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
     * 长宽高
     */
    @TableId(value = "len_wid_height")
    private String lenWidHeight;

    /**
     * 重量
     */
    @TableId(value = "weight")
    private Double weight;

    /**
     * 最大可用购物券比例
     */
    @TableId(value = "score_scale")
    private BigDecimal scoreScale;

    public BigDecimal getScoreScale() {
        return scoreScale;
    }

    public void setScoreScale(BigDecimal scoreScale) {
        this.scoreScale = scoreScale;
    }

    /**
     * 发货城市
     */
    @TableId(value = "city")
    private String city;

    /**
     * 可用库存数（库存总数-占用数量）
     */
    @TableId(value = "usable_stock")
    private Integer usableStock;

    /**
     * sku销量
     */
    @TableId(value = "sku_salt_volume")
    private Integer skuSaltVolume;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
