package com.aomi.pay.controller;


import com.aomi.pay.config.DbContextHolder;
import com.aomi.pay.enums.DBTypeEnum;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.NotifyService;
import com.aomi.pay.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 交易类接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "NotifyController", tags = "交易接口管理")
public class NotifyController {

    @Resource
    private NotifyService paymentOrderService;

    @ApiOperation(value = "支付回调")
    @PostMapping("/payNotify")
    public void payNotify(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);

        JSONObject jsonObject = JSONObject.fromObject(request);
        Object data = jsonObject.get("data");
        if (!StringUtils.isEmpty(data)) {
            NotifyRequest notifyRequest = GeneralConvertorUtil.convertor(JSONObject.fromObject(data), NotifyRequest.class);
            //进事务前手动切数据源
            DbContextHolder.setDbType(DBTypeEnum.order);
            paymentOrderService.payNotify(notifyRequest);
        }
        log.info("--------支付回调结束--------");
    }

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public void test(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);


        log.info("--------支付回调结束--------");
    }

}
