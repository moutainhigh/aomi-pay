package com.cloudbest.payment.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


public class PaymentBillDTO implements Serializable {
    /*private Long merchantId;
    private String merchantName;
    private Long merchantAppId;*/
    private String merchantOrderNo;
    private String channelOrderNo;
    private String productName;
    private String createTime;
    private String posTime;
    private String equipmentNo;
    private String userAccount;
    private BigDecimal totalAmount;
    private BigDecimal tradeAmount;
    private BigDecimal discountAmount;
    private BigDecimal serviceFee;
    private String refundOrderNo;
    private BigDecimal refundMoney;
    private String platformChannel;
    //private String storeId;  //自己平台的商户编号，再根据该编号来确认是否是本平台的订单
    private String remark;

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPosTime() {
        return posTime;
    }

    public void setPosTime(String posTime) {
        this.posTime = posTime;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getPlatformChannel() {
        return platformChannel;
    }

    public void setPlatformChannel(String platformChannel) {
        this.platformChannel = platformChannel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PaymentBillDTO{" +
                "merchantOrderNo='" + merchantOrderNo + '\'' +
                ", channelOrderNo='" + channelOrderNo + '\'' +
                ", productName='" + productName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", posTime='" + posTime + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", totalAmount=" + totalAmount +
                ", tradeAmount=" + tradeAmount +
                ", discountAmount=" + discountAmount +
                ", serviceFee=" + serviceFee +
                ", refundOrderNo='" + refundOrderNo + '\'' +
                ", refundMoney=" + refundMoney +
                ", platformChannel='" + platformChannel + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
