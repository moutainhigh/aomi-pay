/*
 * Copyright (C) 2011- 2020 ShenZhen iBOXCHAIN Information Technology Co.,Ltd.
 * All right reserved.
 *  This software is the confidential and proprietary
 * information of iBOXCHAIN Company of China.
 *  ("Confidential Information"). You shall not disclose
 *  such Confidential Information and shall use it only
 *  in accordance with the terms of the contract agreement
 *   you entered into with iBOXCHAIN inc.
 */

package com.aomi.pay.util;

import com.alibaba.fastjson.JSON;
import com.aomi.pay.constants.ApiConstans;
import com.aomi.pay.domain.CommonErrorCode;
import com.iboxpay.open.security.constant.SecretType;
import com.iboxpay.open.security.constant.SignType;
import com.iboxpay.open.security.util.SecretUtil;
import com.iboxpay.open.security.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: public-open-appmgr
 * @description: 环迅接口公共调用util
 * @author: hdq
 * @create: 2020-08-4 13:19
 **/
@Slf4j
@RefreshScope
@Component
public class SdkUtil {

    /**
     * 环迅接口调用地址
     */
    private static String URL;
    /**
     * 敏感信息加密key
     */
    private static String KEY;
    /**
     * 环迅平台分配的应用id
     */
    private static String UP_APPID;
    /**
     * 秘钥
     */
    private static String APP_SECRET;
    /**
     * 加密秘钥版本号
     */
    private static String KEY_VERSION;

    @Value("${api-url}")
    public void setUrl(String url) {
        URL = url;
    }

    @Value("${key}")
    public void setKey(String key) {
        KEY = key;
    }

    @Value("${up-appid}")
    public void setAppId(String appId) {
        UP_APPID = appId;
    }

    @Value("${app-secret}")
    public void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }

    @Value("${key-version}")
    public void setKeyVersion(String keyVersion) {
        KEY_VERSION = keyVersion;
    }

    /**
     * @author hdq
     * @date 2020/8/4
     * @desc 统一接口调用
     **/
    public static String post(Object object, String route) throws IOException {
        log.info("---------接口调用---------");
        // map存放请求参数
        Map<String, String> params = new HashMap<>();
        params.put("data", JSON.toJSONString(object));
        params.put("key-version", KEY_VERSION);
        // 对请求参数进行签名，返回的map包含验证所需的公共参数
        params = SignUtil.sign(params, UP_APPID, APP_SECRET, SignType.MD5);
        //忽略SSL认证调用接口
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).sslSocketFactory(SSLSocketClientUtil.getSSLSocketFactory()).hostnameVerifier(SSLSocketClientUtil.getHostnameVerifier()).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.post(okhttp3.RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(params))).url(URL.concat(route)).build();
        log.info("params:{}", JSON.toJSONString(params));
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.info("error:接口请求失败");
            CommonExceptionUtils.throwSystemException(CommonErrorCode.UNKNOWN);
        }
        log.info("response:{}", StringUtils.isEmpty(response) ? null : response.toString());
        String content =  response.body().string();
        log.info("response.body:{}", StringUtils.isEmpty(response.body()) ? null : content);
        if (!StringUtils.isEmpty(response.body())) {
            JSONObject jsonObject = JSONObject.fromObject(content);
            if (ApiConstans.RESULT_CODE_FAIL.equals(jsonObject.getString(ApiConstans.RESULT_CODE_NAME))) {
                CommonExceptionUtils.throwBusinessException(Integer.parseInt(jsonObject.getString(ApiConstans.ERROR_CODE_NAME)), "api接口调用失败：".concat(jsonObject.getString(ApiConstans.ERROR_DESC_NAME)));
            }
        }
        return content;
    }

    /**
     * @author hdq
     * @date 2020/8/4
     * @desc 敏感信息加密
     **/
    public static String encrypt(String content) {
        String result = "";
        if (StringUtil.isNotBlank(content)) {
            result = SecretUtil.encrypt(content, KEY, SecretType.AES);
        }
        return result;
    }

    public static void main(String[] args) {
     /*   String encrypt = SecretUtil
                .encrypt("430981199403131717", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);

        System.out.println(encrypt);

        String decrypt = SecretUtil
                .decrypt("qqHxg/8yoZDIyZigXTD3O9GBLuAjz47IVyP8ueWMsdU=", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);
*/
        String decrypt = SecretUtil
                .decrypt("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=", KEY, SecretType.AES);
        String decrypt1 = SecretUtil
                .decrypt("GD2miQARzl1DpNlKSRHrc9lDoXUTDGtHgytmaXl2371PQpe78I9K/NhKYHP9cTCG", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);
        String decrypt2 = SecretUtil
                .decrypt("VfQY65Q0OPO40V9PuGp6Qi7mK49nW23kzxnfXw26XK23hTiJACAE1ss6lZf8QgLG", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);

        System.out.println(decrypt);
        System.out.println(decrypt1);
        System.out.println(decrypt2);
    }

}