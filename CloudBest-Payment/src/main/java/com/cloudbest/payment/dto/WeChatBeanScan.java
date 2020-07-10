package com.cloudbest.payment.dto;

import lombok.Data;

import java.io.Serializable;


public class WeChatBeanScan implements Serializable {
    //参考微信api列出来需要字段

    //微信付款码支付的必传参数
    private String appid;//公众号 此处不用传，设置sdk里有设置
    private String mchId;//商户号
    private String nonceStr;//随机字符串
    private String sign;//签名  此处不用设置sdk里有设置
    private String body;//商品描述
    private String outTradeNo;//商户订单号
    private Integer totalFee;//标价金额 订单总金额，单位为分，只能为整数，详见支付金额
    private String spbillCreateIp;//终端IP 8.8.8.8	支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
    private String authCode;//授权码 120061098828009406	扫码支付授权码，设备读取用户微信中的条码或者二维码信息（注：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头）

    //微信付款码支付的非传参数
    private String signType;//签名类型， 此处不用设置sdk里有设置   目前支持HMAC-SHA256和MD5，沙箱环境为MD5   正是环境是HMAC-SHA256 如果是此类型需设置 交易过程生成签名的密钥
    private String detail;// 商品详情  单品优惠功能字段，需要接入详见单品优惠详细说明 https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_2
    private String attach;// 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
    private String fee_type;// 货币类型 fee_type 否 默认人民币：CNY
    private String goods_tag;// 订单优惠标记，代金券或立减优惠功能的参数，详见代金券或立减优惠
    private String limit_pay;// 指定支付方式 no_credit--指定不能使用信用卡支付
    private String time_start;// 交易起始时间 String(14)	20091225091010	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
    private String time_expire;// 交易结束时间 String(14)	20091227091010	订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。注意：最短失效时间间隔需大于1分钟
    private String receipt; //电子发票入口开放标识 Y	Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
    private String sceneInfo;//场景信息 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，字段详细说明请点击行前的+展开

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    @Override
    public String toString() {
        return "WeChatBeanScan{" +
                "appid='" + appid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", authCode='" + authCode + '\'' +
                ", signType='" + signType + '\'' +
                ", detail='" + detail + '\'' +
                ", attach='" + attach + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", goods_tag='" + goods_tag + '\'' +
                ", limit_pay='" + limit_pay + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", receipt='" + receipt + '\'' +
                ", sceneInfo='" + sceneInfo + '\'' +
                '}';
    }
}
