package com.cloudbest.order.vo;


import lombok.Data;

import java.util.List;

//提交订单页面的数据模型
@Data
public class OrderConfirmVO {
    //收货地址列表
    //private List<CustomerAddr> addresses;
    //送货清单 OrderItemVO
    private List<OrderItemVO> orderItems;
    //订单令牌 ，放重复提交
    private String orderToken;

    @Override
    public String toString() {
        return "OrderConfirmVO{" +
                ", orderItems=" + orderItems +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }


    public List<OrderItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }
}
