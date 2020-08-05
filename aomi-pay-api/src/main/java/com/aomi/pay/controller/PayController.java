package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
@RefreshScope
@Api(value = "PayController", tags = "交易类(无卡类)接口管理")
@RequestMapping("/pay")
public class PayController {

    /**
     * 扫码支付
     */
    @Value("${api_route.pay.online_trade}")
    private String routeOnlineTrade;


    @ApiOperation(value = "扫码支付")
    @PostMapping("/onlineTrade")
    public BaseResponse onlineTrade() throws Exception {
        log.info("--------扫码支付--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        //paramsData.put("instId", "015001");
        //paramsData.put("mchtNo", "015370109123528");
        paramsData.put("serviceId", "hx.alipay.native");
        paramsData.put("version", "1.0.0");
        paramsData.put("isvOrgId", "015001");
        paramsData.put("productCode", "000001");
        paramsData.put("settleType", "DREAL");
        paramsData.put("outTradeNo", "16512315615615132121");
        paramsData.put("merchantNo", "015370109123528");
        paramsData.put("subject", "测试");
        paramsData.put("amount", "0.01");
        paramsData.put("notifyUrl", "192.168.103.250:8179/order/payment/notify");
        String result = SdkUtil.post(paramsData, routeOnlineTrade);
        JSONObject jsonObject = JSONObject.fromObject(result);
        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
    }

}
