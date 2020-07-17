package com.cloudbest.order.vo;

import lombok.Data;

import java.math.BigDecimal;

//提交订单页   商品的数据信息

@Data
public class OrderItemVO {


    @Override
    public String toString() {
        return "OrderItemVO{" +
                "skuId=" + skuId +
                ", spuId=" + spuId +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", status=" + status +
                ", scoreScale=" + scoreScale +
                ", weight=" + weight +
                '}';
    }

    private Integer skuId;//商品id(skuId)
    private Integer spuId;//商品id(spuId)
    private String image;//图片信息
    private BigDecimal price;//加入购物车时的价格
    private Integer count;//数量
    private Boolean status;//是否有货
    //private List skuAttrValue;//商品规格参数
    private Double scoreScale; //商品允许使用的购物券最大比例
    private Double weight;//商品重量



    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getScoreScale() {
        return scoreScale;
    }

    public void setScoreScale(Double scoreScale) {
        this.scoreScale = scoreScale;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }





}
