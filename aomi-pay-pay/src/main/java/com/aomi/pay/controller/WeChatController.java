package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.service.AliPayService;
import com.aomi.pay.service.WeChatService;
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
 * @date 2020/8/14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "WeChatController", tags = "微信接口管理")
@RequestMapping("/wxPay")
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @ApiOperation(value = "获取微信openId")
    @GetMapping("/getOpenId")
    public BaseResponse getOpenId(@RequestParam String code) throws Exception {
        log.info("------------获取微信openId------------");
        //根据code获取openId
        String openId = weChatService.getOpenId(code);
        log.info("--------获取微信openId--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, openId);
    }

}
