package com.aomi.pay.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * 商户入网类接口Controller
 *
 * @author : hdq
 * @date 2020/8/3 11:31
 */
@Slf4j
@CrossOrigin
@RestController
@RefreshScope
@Api(value = "TestController", tags = "商户入网类接口管理")
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public void uploadImg() throws Exception {
        log.info("--------test--------");
    }

}
