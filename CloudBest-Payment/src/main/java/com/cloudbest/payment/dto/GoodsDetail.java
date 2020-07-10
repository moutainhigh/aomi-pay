package com.cloudbest.payment.dto;

import lombok.Data;

import java.io.Serializable;


public class GoodsDetail implements Serializable {
    /**
     * 支付宝定义的统一商品编号
     */
    private String alipayGoodsId;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 商品类目树，从商品类目根节点到叶子节点的类目id组成，类目id值使用|分割
     */
    private String categoriesTree;

    /**
     * 商品类目
     */
    private String goodsCategory;

    /**
     * 商品的编号
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品单价，单位为元
     */
    private String price;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 商品的展示地址
     */
    private String showUrl;

    public String getAlipayGoodsId() {
        return alipayGoodsId;
    }

    public void setAlipayGoodsId(String alipayGoodsId) {
        this.alipayGoodsId = alipayGoodsId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategoriesTree() {
        return categoriesTree;
    }

    public void setCategoriesTree(String categoriesTree) {
        this.categoriesTree = categoriesTree;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "alipayGoodsId='" + alipayGoodsId + '\'' +
                ", body='" + body + '\'' +
                ", categoriesTree='" + categoriesTree + '\'' +
                ", goodsCategory='" + goodsCategory + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", showUrl='" + showUrl + '\'' +
                '}';
    }
}
