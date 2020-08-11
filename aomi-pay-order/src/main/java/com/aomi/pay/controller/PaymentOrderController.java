package com.aomi.pay.controller;


import com.aomi.pay.constants.ApiConstans;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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
        log.info("--------h5支付开始--------req:{}",req);
        //参数校验
        ValidateUtil.valid(req);
        String payInfo = paymentOrderService.jsPay(Long.valueOf(req.getId()),new BigDecimal(req.getAmount()),Integer.parseInt(req.getPayType()));
        log.info("--------h5支付结束--------");
        return new BaseResponse(CommonErrorCode.SUCCESS,payInfo);
    }

    @ApiOperation(value = "支付回调")
    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request) throws Exception {
        log.info("------------支付回调开始------------");
        //获取通知信息
        String tradeStatus = new String(request.getParameter(ApiConstans.TRADE_STATUS_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String platformMerchantId = new String(request.getParameter(ApiConstans.MERCHANT_NO_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String tradeNo = new String(request.getParameter(ApiConstans.TEADE_NO_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String outTradeNo = new String(request.getParameter(ApiConstans.OUT_TRADE_NO).getBytes("ISO-8859-1"), "UTF-8");
        paymentOrderService.payNotify(tradeStatus,platformMerchantId,tradeNo,outTradeNo);
        log.info("--------支付回调结束--------");
    }

}
