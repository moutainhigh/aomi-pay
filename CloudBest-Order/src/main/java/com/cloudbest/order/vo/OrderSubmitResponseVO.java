package com.cloudbest.order.vo;


import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.entity.SecondarilyEntity;
import lombok.Data;

//提交订单后要响应的数据
@Data
public class OrderSubmitResponseVO {

    //private Integer code;//1-不可重复提交或者页面已过期 2-库存不足 3-价格校验不合法
    private MainEntity mainEntity;//主订单数据
    private SecondarilyEntity secondarilyEntity;//子订单数据

    @Override
    public String toString() {
        return "OrderSubmitResponseVO{" +
                "mainEntity=" + mainEntity +
                ", secondarilyEntity=" + secondarilyEntity +
                '}';
    }

    public MainEntity getMainEntity() {
        return mainEntity;
    }

    public void setMainEntity(MainEntity mainEntity) {
        this.mainEntity = mainEntity;
    }
}
