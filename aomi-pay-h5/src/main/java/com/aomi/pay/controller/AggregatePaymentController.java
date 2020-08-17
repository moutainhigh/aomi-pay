package com.aomi.pay.controller;

import com.aomi.pay.constants.H5Constants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.feign.PayClient;
import com.aomi.pay.service.AggregatePaymentService;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private AggregatePaymentService aggregatePaymentService;

    @GetMapping("/{fixedQrCode}")
    public String hello(HttpServletRequest request, @PathVariable("fixedQrCode") String fixedQrCode, HttpServletResponse httpServletResponse, Model model) throws Exception {
        String userAgent = request.getHeader(H5Constants.USER_AGENT_NAME);

        if (StringUtil.isNotBlank(userAgent) && userAgent.contains(H5Constants.USER_AGENT_ALIPAY)) {
            aggregatePaymentService.getUserId(fixedQrCode, request, httpServletResponse, model);
            return "pay_ali";
        } else if (StringUtil.isNotBlank(userAgent) && userAgent.contains(H5Constants.USER_AGENT_WX)) {
            aggregatePaymentService.getOpenId(fixedQrCode, request, httpServletResponse, model);
            return "pay_wx";
        } else {
            return "no_support";
        }
    }

    /**
     * @author hdq
     * @date 2020/8/15
     * @desc 成功页
     **/
    @GetMapping("/success")
    public String success() {
        return "success";
    }

    /**
     * @author hdq
     * @date 2020/8/15
     * @desc 失败页
     **/
    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }
}
