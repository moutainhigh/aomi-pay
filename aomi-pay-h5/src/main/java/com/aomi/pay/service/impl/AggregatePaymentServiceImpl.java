package com.aomi.pay.service.impl;

import com.aomi.pay.constants.H5Constants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.feign.PayClient;
import com.aomi.pay.service.AggregatePaymentService;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付宝Service实现类
 *
 * @author : hdq
 * @date 2020/8/15
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class AggregatePaymentServiceImpl implements AggregatePaymentService {


    @Resource
    private PayClient payClient;

    /**
     * 请求auth_code的支付宝链接
     */
    @Value("${alipay.auth_code_url}")
    private String aliAuthCodeUrl;

    /**
     * 支付宝appid
     */
    @Value("${alipay.appid}")
    private String aliAppId;

    /**
     * 支付宝回调地址
     */
    @Value("${alipay.redirect_uri}")
    private String aliRedirectUri;

    /**
     * 请求code的微信链接
     */
    @Value("${wx.code_url}")
    private String wxCodeUrl;

    /**
     * 微信appid
     */
    @Value("${wx.appid}")
    private String wxAppId;

    /**
     * 微信回调地址
     */
    @Value("${alipay.redirect_uri}")
    private String wxRedirectUri;

    /**
     * @param request             HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @author hdq
     * @date 2020/8/15
     * @desc 获取支付宝userid
     */
    @Override
    public void getUserId(String fixedQrCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        String userId = getUserIdByCookies(request);
        if (StringUtil.isBlank(userId)) {
            String authCode = request.getParameter(H5Constants.AUTH_CODE);
            log.info("auth_code:{}", authCode);
            if (StringUtil.isNotBlank(authCode)) {
                BaseResponse response = payClient.getUserId(authCode);
                if (CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
                    userId = response.getData().toString();
                    Cookie userCookie = new Cookie(H5Constants.USER_ID_NAME, userId);
                    log.info("useId存cookie:{}",userId);
                    userCookie.setMaxAge(H5Constants.COOKIE_MAX_AGE);
                    userCookie.setPath("/");
                    httpServletResponse.addCookie(userCookie);
                }
            } else {
                String url = aliAuthCodeUrl.concat("?").concat("app_id=").concat(aliAppId).concat("&redirect_uri=").concat(aliRedirectUri).concat(fixedQrCode).concat("&response_type=code&scope=auth_base");
                httpServletResponse.setStatus(302);
                httpServletResponse.setHeader("location", url);
            }
        }
    }

    /**
     * @param request             HttpServletResponse
     * @param httpServletResponse HttpServletRequest
     * @author hdq
     * @date 2020/8/15
     * @desc 获取微信
     */
    @Override
    public void getOpenId(String fixedQrCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        String userId = getUserIdByCookies(request);
        if (StringUtil.isBlank(userId)) {
            String code = request.getParameter(H5Constants.CODE);
            log.info("code:{}", code);
            if (StringUtil.isNotBlank(code)) {
                BaseResponse response = payClient.getOpenId(code);
                if (CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
                    userId = response.getData().toString();
                    Cookie userCookie = new Cookie(H5Constants.USER_ID_NAME, userId);
                    log.info("useId存cookie:{}",userId);
                    userCookie.setMaxAge(H5Constants.COOKIE_MAX_AGE);
                    userCookie.setPath("/");
                    httpServletResponse.addCookie(userCookie);
                }
            } else {
                String url = wxCodeUrl.concat("?appid=").concat(wxAppId).concat("&redirect_uri=").concat(wxRedirectUri).concat(fixedQrCode).concat("&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect;");
                httpServletResponse.setStatus(302);
                httpServletResponse.setHeader("location", url);
            }
        }
    }


    /**
     * @author hdq
     * @date 2020/8/15
     * @desc 从cookies获取userid
     **/
    public String getUserIdByCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userId = "";
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }
        log.info("userId:{}", userId);
        return userId;
    }
}



























