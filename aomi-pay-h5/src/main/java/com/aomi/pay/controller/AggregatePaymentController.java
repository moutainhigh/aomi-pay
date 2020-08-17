package com.aomi.pay.controller;

import com.aomi.pay.constants.H5Constants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.feign.PayClient;
import com.aomi.pay.service.AggregatePaymentService;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * h5Controller
 *
 * @author : hdq
 * @date 2020/8/13
 */
@Slf4j
@CrossOrigin
@Controller
public class AggregatePaymentController {

    @Resource
    private PayClient payClient;

    @Resource
    private AggregatePaymentService aggregatePaymentService;

    @GetMapping("/hello1/{merchantId}")
    public String hello1(HttpServletRequest request, @PathVariable("merchantId") String merchantId, HttpServletResponse httpServletResponse) throws Exception {
        log.info("--------hello1--------");
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
        if (StringUtil.isBlank(userId)) {
            String authCode = request.getParameter("auth_code");
            log.info("auth_code:{}", authCode);
            if (StringUtil.isNotBlank(authCode)) {
                BaseResponse response = payClient.getUserId(authCode);
                if (CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
                    userId = response.getData().toString();
                    Cookie userCookie = new Cookie("userId", userId);
                    //存活期为一个月 30*24*60*60
                    userCookie.setMaxAge(30 * 24 * 60 * 60);
                    userCookie.setPath("/");
                    httpServletResponse.addCookie(userCookie);

                }
            } else {
                String url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2021001168632988&redirect_uri=https%3A%2F%2Fqr.cloudbest.shop%2Fhello1%2F".concat(merchantId).concat("&response_type=code&scope=auth_base");
                httpServletResponse.setStatus(302);
                httpServletResponse.setHeader("location", url);
            }
        }
        return "pay_ali";
    }

    @GetMapping("/hello2/{merchantId}")
    public String hello2(HttpServletRequest request, @PathVariable("merchantId") String merchantId, HttpServletResponse httpServletResponse) throws Exception {
        log.info("--------hello2--------");
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
        if (StringUtil.isBlank(userId)) {
            String code = request.getParameter("code");
            log.info("code:{}", code);
            if (StringUtil.isNotBlank(code)) {
                BaseResponse response = payClient.getOpenId(code);
                if (CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
                    userId = response.getData().toString();
                    Cookie userCookie = new Cookie("userId", userId);
                    //存活期为一个月 30*24*60*60
                    userCookie.setMaxAge(30 * 24 * 60 * 60);
                    userCookie.setPath("/");
                    httpServletResponse.addCookie(userCookie);

                }
            } else {
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb1b1558437e9b12&redirect_uri=https://qr.cloudbest.shop/hello2/".concat(merchantId).concat("&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect;");
                httpServletResponse.setStatus(302);
                httpServletResponse.setHeader("location", url);
            }
        }
        return "pay_wx";
    }

    @GetMapping("/{fixedQrCode}")
    public String hello(HttpServletRequest request, @PathVariable("fixedQrCode") String fixedQrCode, HttpServletResponse httpServletResponse) throws Exception {
        String userAgent = request.getHeader(H5Constants.USER_AGENT_NAME);

        if (StringUtil.isNotBlank(userAgent) && userAgent.contains(H5Constants.USER_AGENT_ALIPAY)) {
            aggregatePaymentService.getUserId(fixedQrCode,request,httpServletResponse);
            return "pay_ali";
        } else if (StringUtil.isNotBlank(userAgent) && userAgent.contains(H5Constants.USER_AGENT_WX)) {
            aggregatePaymentService.getOpenId(fixedQrCode,request,httpServletResponse);
            return "pay_wx";
        } else {
            return "no_support";
        }
    }

 /*   @GetMapping("/{merchantId}")
    public String aggregatePayment(HttpServletRequest request, @PathVariable("merchantId") String merchantId, HttpServletResponse httpServletResponse) throws Exception {
        String userAgent = request.getHeader("user-agent");
        String url = "";
        if (userAgent != null && userAgent.contains("AlipayClient")) {
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("location", "https://qr.cloudbest.shop/hello1/".concat(merchantId));
            return "pay_ali";
        } else if (userAgent != null && userAgent.contains("MicroMessenger")) {
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("location", "https://qr.cloudbest.shop/hello2/".concat(merchantId));
            return "pay_wx";
        } else {
            return "123123";
        }
    }*/

    /**
     * @author  hdq
     * @date  2020/8/15
     * @desc 成功页
     **/
    @GetMapping("/success")
    public String success() {
        return "success";
    }

    /**
     * @author  hdq
     * @date  2020/8/15
     * @desc 失败页
     **/
    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }
}
