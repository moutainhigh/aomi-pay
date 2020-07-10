package com.cloudbest.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 预支付响应
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeCreateResponse implements Serializable {

    /**
     * 原始支付渠道交易号，目前了解的情况是：支付宝直接返回，微信在支付结果通知才给返回
     */
    private String payChannelTradeNo;

    /**
     *  预支付响应内容 json字符串
     */
    private String responseContent;

    public String getPayChannelTradeNo() {
        return payChannelTradeNo;
    }

    public void setPayChannelTradeNo(String payChannelTradeNo) {
        this.payChannelTradeNo = payChannelTradeNo;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    @Override
    public String toString() {
        return "TradeCreateResponse{" +
                "payChannelTradeNo='" + payChannelTradeNo + '\'' +
                ", responseContent='" + responseContent + '\'' +
                '}';
    }
}

