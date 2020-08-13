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
import com.aomi.pay.constants.ApiConstants;
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
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: public-open-appmgr
 * @description: 环迅接口公共调用util
 * @author: hdq
 * @create: 2020-08-07
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
    public static Object post(Object object, String route) throws IOException {
        log.info("---------接口调用开始---------");
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
            //当接口返回resultCode为不1 或者 errorCode 不为空时 抛出异常信息
            if (ApiConstants.RESULT_CODE_FAIL.equals(jsonObject.getString(ApiConstants.RESULT_CODE_NAME))) {
                CommonExceptionUtils.throwBusinessException((jsonObject.getString(ApiConstants.ERROR_CODE_NAME)), "api接口返回异常信息：".concat(jsonObject.getString(ApiConstants.ERROR_DESC_NAME)));
            }
            if(jsonObject.has(ApiConstants.ERROR_CODE_NAME)){
                CommonExceptionUtils.throwBusinessException((jsonObject.getString(ApiConstants.ERROR_CODE_NAME)), "api接口返回异常信息：".concat(jsonObject.getString(ApiConstants.ERROR_DESC_NAME)));
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(content);
        log.info("---------接口调用结束---------");
        return jsonObject.get(ApiConstants.ERROR_DESC_DATA);
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

    /**
     * @author hdq
     * @date 2020/8/4
     * @desc 敏感信息加密
     **/
    public static String decrypt(String content) {
        String result = "";
        if (StringUtil.isNotBlank(content)) {
            result = SecretUtil.decrypt(content, KEY, SecretType.AES);
        }
        return result;
    }

    /**
     * @desc  各种测试节省时间
     **/
    public static void main(String[] args) throws Exception{
/*        String encrypt = SecretUtil
                .encrypt("430981199403131717", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);

        System.out.println(encrypt);

        String decrypt = SecretUtil
                .decrypt("qqHxg/8yoZDIyZigXTD3O9GBLuAjz47IVyP8ueWMsdU=", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);
        */

        /*String decrypt = SecretUtil
                .decrypt("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=", KEY, SecretType.AES);
        String decrypt1 = SecretUtil
                .decrypt("GD2miQARzl1DpNlKSRHrc9lDoXUTDGtHgytmaXl2371PQpe78I9K/NhKYHP9cTCG", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);
        String decrypt2 = SecretUtil
                .decrypt("VfQY65Q0OPO40V9PuGp6Qi7mK49nW23kzxnfXw26XK23hTiJACAE1ss6lZf8QgLG", "7kyB+aUhF14f+zbE0ERd4Q==", SecretType.AES);

        System.out.println(decrypt);
        System.out.println(decrypt1);
        System.out.println(decrypt2);*/

        Map<String, Object> paramsData = setMap1();
        String route = "/online/trade";
        //String route = "/mcht/add_product";
        //String route = "/online/query";
        //String route = "/mcht/update_org_mcht";

        log.info("---------接口调用---------");
        // map存放请求参数
        Map<String, String> params = new HashMap<>();
        params.put("data", JSON.toJSONString(paramsData));
        params.put("key-version", "29");
        // 对请求参数进行签名，返回的map包含验证所需的公共参数
        params = SignUtil.sign(params, "135459893032255489", "f88ebda65bdb4d63ac1194ac7c2af625", SignType.MD5);
        //忽略SSL认证调用接口
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).sslSocketFactory(SSLSocketClientUtil.getSSLSocketFactory()).hostnameVerifier(SSLSocketClientUtil.getHostnameVerifier()).build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.post(okhttp3.RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(params))).url("https://payapi-sandbox.imipay.com".concat(route)).build();
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
            if (ApiConstants.RESULT_CODE_FAIL.equals(jsonObject.getString(ApiConstants.RESULT_CODE_NAME))) {
                CommonExceptionUtils.throwBusinessException((jsonObject.getString(ApiConstants.ERROR_CODE_NAME)), "api接口调用失败：".concat(jsonObject.getString(ApiConstants.ERROR_DESC_NAME)));
            }
        }

        JSONObject jsonObject = JSONObject.fromObject(content);


            //java.net.URL url = new URL("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-image-1594099431722.jpg");

    }

    //测试图片url转byte[]转base64
    public static String img(){
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try{
            url = new URL("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-image-1594099431722.jpg");
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();

            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=is.read(buffer)) != -1 ){

                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度

                outStream.write(buffer, 0, len);

            }
            // 对字节数组Base64编码
            return encode(outStream.toByteArray());

        }catch (Exception e) {
            e.printStackTrace();
        }
        return encode(outStream.toByteArray());
    }
    public static String encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    public static String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    //测试商户开通产品
    public static Map<String,Object> setMap(){
        Map<String, Object> paramsData = new HashMap<>();
        paramsData.put("instId", "015001");
        paramsData.put("mchtNo", "015370109123528");
        //paramsData.put("productCode", "100010");//支付宝
        //paramsData.put("modelId", "MHN90144");
        //paramsData.put("productCode", "100011");//微信
        //paramsData.put("modelId", "MHN11201");
        paramsData.put("productCode", "100018");//银联
        paramsData.put("modelId", "MHN11201");
        return paramsData;
    }

    //测试h5支付
    public static Map<String,Object> setMap1(){
        Map<String, Object> paramsData = new HashMap<>();

        paramsData.put("serviceId", "hx.wechat.jspay");//微信
        paramsData.put("version", "1.0.0");
        paramsData.put("isvOrgId", "015001");
        //paramsData.put("productCode", "100010");//支付宝
        paramsData.put("productCode", "100011");//微信
        paramsData.put("settleType", "DREAL");
        paramsData.put("outTradeNo", "16512315615615132133");
        //paramsData.put("merchantNo", "015310109123536");
        paramsData.put("merchantNo", "015440309175982");
        paramsData.put("subject", "测试");
        paramsData.put("amount", "1");
        //paramsData.put("userId", "2021001168632988");
        paramsData.put("userId", "oXpzSv9AcnTkxsErnGUKCDzZIZBs");
        paramsData.put("subAppid", "wx57f9d11132fc79c1");
        //paramsData.put("subAppid", "wxeb1b1558437e9b12");
        paramsData.put("notifyUrl", "192.168.103.250:8179/order/payment/notify");
        return paramsData;
    }

    //测试订单查询
    public static Map<String,Object> setMap2(){
        Map<String, Object> paramsData = new HashMap<>();

        paramsData.put("serviceId", "hx.unified.query");
        paramsData.put("version", "1.0.0");
        paramsData.put("isvOrgId", "015001");
        paramsData.put("tradeType", "31");
        paramsData.put("outTradeNo", "16512315615615132125");
        //paramsData.put("merchantNo", "015370109123528");
        paramsData.put("merchantNo", "015440309175982");
        return paramsData;
    }

    //测试查询商户入网信息
    public static Map<String,Object> setMap3(){
        Map<String, Object> paramsData = new HashMap<>();
        paramsData.put("instId", "015001");
        paramsData.put("mchtNo", "015370109123528");
        paramsData.put("instMchtNo", "test100000002");
        return paramsData;
    }


    //测试查询商户审核状态
    public static Map<String,Object> setMap4(){
        Map<String, Object> paramsData = new HashMap<>();
        paramsData.put("instId", "015001");
        paramsData.put("mchtNo", "015310109123536");
        paramsData.put("instMchtNo", "10000000001");
        return paramsData;
    }
}