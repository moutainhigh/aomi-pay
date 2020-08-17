package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 交易类(无卡类)接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "PaymentController", tags = "交易接口管理")
@RequestMapping("/payment")
public class PaymentOrderController {

    @Resource
    private PaymentOrderService paymentOrderService;

    @ApiOperation(value = "h5支付")
    @PostMapping("/jsPay")
    public BaseResponse jsPay(@RequestBody JsPayRequest req) throws Exception {
        log.info("--------h5支付开始--------req:{}", req);
        //参数校验
        ValidateUtil.valid(req);
        String payInfo = paymentOrderService.jsPay(req);
        log.info("--------h5支付结束--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, payInfo);
    }

    @ApiOperation(value = "支付回调")
    @PostMapping("/notify")
    public void payNotify(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);

        JSONObject jsonObject = JSONObject.fromObject(request);
        Object data = jsonObject.get("data");
        if(!StringUtils.isEmpty(data)){
            NotifyRequest notifyRequest = GeneralConvertorUtil.convertor(JSONObject.fromObject(data), NotifyRequest.class);
            paymentOrderService.payNotify(notifyRequest);
        }
        log.info("--------支付回调结束--------");
    }

}
