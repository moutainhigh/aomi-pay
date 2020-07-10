package com.cloudbest.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("order_item")
public class ItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", itemId=" + itemId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
