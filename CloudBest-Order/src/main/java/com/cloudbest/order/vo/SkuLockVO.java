package com.cloudbest.order.vo;


import lombok.Data;

//所库存对象
@Data
public class SkuLockVO {
    private Integer skuId;//商品id
    private Integer stockId;//库存表主键
    private Boolean lock; // 锁定成功true，锁定失败false
    private Integer num;//锁定数量
    private String orderToken;//订单编号

    @Override
    public String toString() {
        return "SkuLockVO{" +
                "skuId=" + skuId +
                ", stockId=" + stockId +
                ", lock=" + lock +
                ", num=" + num +
                ", orderToken='" + orderToken + '\'' +
                '}';
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }
}
