package com.aomi.pay.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class PaymentController {


    @ApiOperation(value = "支付回调")
    @PostMapping("/notify")
    public void onlineTrade() throws Exception {
        log.info("--------支付回调开始--------");
        //TODO 待添加逻辑
        log.info("--------支付回调开始--------");
    }

}
