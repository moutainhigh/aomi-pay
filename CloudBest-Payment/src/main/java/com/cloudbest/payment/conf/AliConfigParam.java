package com.cloudbest.payment.conf;
import java.io.Serializable;

public class AliConfigParam implements Serializable {
    //应用id
    public String appId;
    //私钥
    public String rsaPrivateKey;
    //异步回调通知
    public String notifyUrl;
    //同步回调通知
    public String returnUrl;
    //支付宝网关
    public String url;
    //编码方式 UTF-8
    public String charest;
    //格式JSON
    public String format;
    //ali公钥
    public String alipayPublicKey;
    //日志保存路径， 目前不用
    public String logPath;
    //RSA2 户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    public String signtype;

    public String getAppId() {
        return appId;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getUrl() {
        return  url;
    }

    public String getCharest() {
        return charest;
    }

    public String getFormat() {
        return format;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getLogPath() {
        return logPath;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCharest(String charest) {
        this.charest = charest;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    @Override
    public String toString() {
        return "AliConfigParam{" +
                "appId='" + appId + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", url='" + url + '\'' +
                ", charest='" + charest + '\'' +
                ", format='" + format + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", logPath='" + logPath + '\'' +
                ", signtype='" + signtype + '\'' +
                '}';
    }

}
