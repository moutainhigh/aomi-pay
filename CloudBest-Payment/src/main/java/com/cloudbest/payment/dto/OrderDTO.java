package com.cloudbest.payment.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


public class OrderDTO implements Serializable {

    private Long id;
    private String tradeNo;//支付订单号
    private Long merchantId;//所属商户
    private Long storeId;//商户下门店，如果未指定，默认是根门店
    private String appId;//此处使用业务id，服务内部使用主键id，服务与服务之间用业务id--appId
    private String payChannel;//原始支付渠道编码
    private String payChannelMchId;//原始渠道商户id
    private String payChannelTradeNo;//原始渠道订单号
    private String channel;//聚合支付的渠道 此处使用渠道编码
    private String outTradeNo;//商户订单号
    private String subject;//商品标题
    private String body;//订单描述
    private String currency;//币种CNY
    private Integer totalAmount;//订单总金额，单位为分
    private String optional;//自定义数据
    private String analysis;//用于统计分析的数据,用户自定义
    private String extra;//特定渠道发起时额外参数
    private String tradeState;//交易状态支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
    private String errorCode;//渠道支付错误码
    private String errorMsg;//渠道支付错误信息
    private String device;//设备
    private String clientIp;//客户端IP
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private LocalDateTime expireTime;//订单过期时间
    private LocalDateTime paySuccessTime;//支付成功时间
    private String openId;
    /**
     * 原始商家的应用id
     */
    private String payChannelMchAppId;
    /**
     * 产品编号，必填
     */
    private String productCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelMchId() {
        return payChannelMchId;
    }

    public void setPayChannelMchId(String payChannelMchId) {
        this.payChannelMchId = payChannelMchId;
    }

    public String getPayChannelTradeNo() {
        return payChannelTradeNo;
    }

    public void setPayChannelTradeNo(String payChannelTradeNo) {
        this.payChannelTradeNo = payChannelTradeNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(LocalDateTime paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPayChannelMchAppId() {
        return payChannelMchAppId;
    }

    public void setPayChannelMchAppId(String payChannelMchAppId) {
        this.payChannelMchAppId = payChannelMchAppId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", tradeNo='" + tradeNo + '\'' +
                ", merchantId=" + merchantId +
                ", storeId=" + storeId +
                ", appId='" + appId + '\'' +
                ", payChannel='" + payChannel + '\'' +
                ", payChannelMchId='" + payChannelMchId + '\'' +
                ", payChannelTradeNo='" + payChannelTradeNo + '\'' +
                ", channel='" + channel + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", currency='" + currency + '\'' +
                ", totalAmount=" + totalAmount +
                ", optional='" + optional + '\'' +
                ", analysis='" + analysis + '\'' +
                ", extra='" + extra + '\'' +
                ", tradeState='" + tradeState + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", device='" + device + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", expireTime=" + expireTime +
                ", paySuccessTime=" + paySuccessTime +
                ", openId='" + openId + '\'' +
                ", payChannelMchAppId='" + payChannelMchAppId + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}
