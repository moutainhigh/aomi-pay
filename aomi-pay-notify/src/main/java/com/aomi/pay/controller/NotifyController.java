package com.aomi.pay.controller;


import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.NotifyBizService;
import com.aomi.pay.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 异步通知类接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "NotifyController", tags = "异步通知接口管理")
@RefreshScope
public class NotifyController {

    @Resource
    private RedisLock redisLock;

    @Resource
    private NotifyBizService notifyBizService;

    @ApiOperation(value = "支付回调")
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);

        JSONObject jsonObject = JSONObject.fromObject(request);
        Object data = jsonObject.get("data");
        if (!StringUtils.isEmpty(data)) {
            NotifyRequest notifyRequest = GeneralConvertorUtil.convertor(JSONObject.fromObject(data), NotifyRequest.class);

            if (redisLock.lock("notify-".concat(notifyRequest.getOutTradeNo()), notifyRequest.getOutTradeNo())) {
                //业务处理
                return notifyBizService.payNotify(notifyRequest);
            } else {
                log.info("---------------------现在已经有回调进来了---------------------");
            }
        }else{
            log.info("------------等下一次回调------------");
            return null;
        }
        log.info("--------支付回调结束--------");
        return "SUCCESS";
    }

}
