package com.cloudbest.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ItemVO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", itemId=" + itemId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", current=" + current +
                ", size=" + size +
                '}';
    }

    /**
     * id
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品id(skuId)
     */
    private Integer productId;

    /**
     * 商品id(spuId)
     */
    private Integer itemId;

    /**
     * 商品名字
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品购买的数量
     */
    private Integer productQuantity;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getItemId() {

        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
