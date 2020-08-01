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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.iboxpay.open.security.util.SignUtil;

import javax.net.ssl.*;

/**
 * @program: public-open-appmgr
 * @description:
 * @author: coledy.liu
 * @create: 2020-07-30 16:19
 **/
public class OldSdkTest {

  public static void main(String[] args) {
    try {
      // map存放请求参数
      Map<String, String> params = new HashMap<>();
      params.put("licenseNo", "440602000434698");
      // 对请求参数进行签名，返回的map包含验证所需的公共参数
      //params = SignUtil.sign(params, "从开放平台得到的appId", "从开放平台得到的appSecret");
      //params = SignUtil.sign(params, "135459893032255489", "7kyB+aUhF14f+zbE0ERd4Q==");
      params = SignUtil.sign(params, "135459893032255489", "f88ebda65bdb4d63ac1194ac7c2af625");


      //测试环境url
      String urlString = "https://payapi-sandbox.imipay.com";
      //生产环境url
      //String urlString = "https://payapi.imipay.com";

      // http请求
      OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).sslSocketFactory(SSLSocketClient.getSSLSocketFactory()).hostnameVerifier(SSLSocketClient.getHostnameVerifier()).build();

      Request.Builder builder = new Request.Builder();
      Request request = builder.post(RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(params))).url(urlString).build();

      Response response = okHttpClient.newCall(request).execute();
      System.out.println(response.toString());
      if (!response.isSuccessful()) {
        System.out.println("error");
      }
      System.out.println(response.body().string());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}