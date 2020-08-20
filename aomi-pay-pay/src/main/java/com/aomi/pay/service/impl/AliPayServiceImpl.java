package com.aomi.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.aomi.pay.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付宝Service实现类
 *
 * @author : hdq
 * @date 2020/8/13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class AliPayServiceImpl implements AliPayService {

    /**
     * 支付宝网关
     */
    @Value("${alipay.url}")
    private String url;

    /**
     * 支付宝appid
     */
    @Value("${alipay.appid}")
    private String appid;

    /**
     * 支付宝网关
     */
    @Value("${alipay.private_key}")
    private String privateKey;

    /**
     * 请求方式
     */
    @Value("${alipay.format}")
    private String format;

    /**
     * 编码格式
     */
    @Value("${alipay.charset}")
    private String charset;

    /**
     * 公钥
     */
    @Value("${alipay.public_key}")
    private String publicKey;

    /**
     * 编码格式
     */
    @Value("${alipay.sign_type}")
    private String signType;


    /**
     * @author hdq
     * @date 2020/8/13
     * @desc 获取支付宝userid
     **/
    @Override
    public String getUserId(String authCode) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, format, charset, publicKey, signType);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        String userId = null;
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            userId = oauthTokenResponse.getUserId();
        } catch (AlipayApiException e) {
            //处理异常
            log.error("支付宝接口异常:{}",e);
        }
        return userId;
    }
}



























