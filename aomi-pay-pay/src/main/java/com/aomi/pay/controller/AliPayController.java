package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.service.AliPayService;
import com.aomi.pay.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 微信接口Controller
 *
 * @author : hdq
 * @date 2020/8/13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "AliPayController", tags = "支付宝接口管理")
@RequestMapping("/aliPay")
public class AliPayController {

    @Resource
    private AliPayService aliPayService;

    @ApiOperation(value = "获取支付宝userId")
    @GetMapping("/getUserId")
    public BaseResponse getUserId(@RequestParam String authCode) throws Exception {
        log.info("------------获取支付宝userId------------");
        //根据authCode获取userId
        String userId = aliPayService.getUserId(authCode);
        log.info("--------获取支付宝userId--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, userId);
    }

}
