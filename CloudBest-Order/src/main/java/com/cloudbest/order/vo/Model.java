package com.cloudbest.order.vo;

import lombok.Data;

@Data
public class Model {

    /*      model.setOutTradeNo(alipayBean.getOutTradeNo());//商户的订单，就是本平台的订单
            model.setTotalAmount(alipayBean.getTotalAmount());//订单金额（元）
            model.setSubject(alipayBean.getSubject());//订单编号
            model.setBody(alipayBean.getBody());//商品描述，可空

      */

    private String outTradeNo; // 订单编号 必填
    private String subject; // 订单名称 必填
    private String totalAmount;  // 付款金额 必填
    private String body; // 商品描述 可空

    @Override
    public String toString() {
        return "Model{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", subject='" + subject + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
