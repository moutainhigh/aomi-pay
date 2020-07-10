package com.cloudbest.payment.conf;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



public class WXConfigParam implements Serializable {

    private String appId;
    private String appSecret;  //是APPID对应的接口密码，用于获取接口调用凭证access_token时使用。
    private String mchId;
    private String key;
    public String returnUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "WXConfigParam{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", mchId='" + mchId + '\'' +
                ", key='" + key + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }
}
