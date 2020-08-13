package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.vo.*;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 商户报备类接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@RefreshScope
@Api(value = "MerchantReportController", tags = "商户报备类接口管理")
@RequestMapping("/merchant/report")
public class MerchantReportController {

    /**
     * 机构id
     */
    @Value("${inst-id}")
    private String intsId;

    /**
     * 查询无卡商户报备状态
     */
    @Value("${api_route.mcht_report.query_register}")
    private String routeQueryRegister;

    /**
     * 微信子商户配置
     */
    @Value("${api_route.mcht_report.config_echat_submcht}")
    private String routeConfigWechatSubmcht;

    /**
     * 微信子商户配置
     */
    @Value("${api_route.mcht_report.config_echat_submcht_query}")
    private String routeConfigWechatSubmchtQuery;

    /**
     * 查询微信子商户配置
     */
    @Value("${api_route.mcht_report.query_unionpay}")
    private String routeQueryUnionpay;

    @ApiOperation(value = "查询无卡商户报备状态")
    @PostMapping("/queryRegister")
    public BaseResponse queryRegister() throws Exception {
        log.info("--------查询无卡商户报备状态--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("instId", intsId);
        //paramsData.put("mchtNo", "015370109123528");
        paramsData.put("mchtNo", "027310103382119");
        Object result = SdkUtil.post(paramsData, routeQueryRegister);
        return new BaseResponse(CommonErrorCode.SUCCESS,result);
    }

    @ApiOperation(value = "微信子商户配置")
    @PostMapping("/configWechatSubmcht")
    public BaseResponse configWechatSubmcht() throws Exception {
        log.info("--------微信子商户配置--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("instId", intsId);
        paramsData.put("mchtNo", "027310103382119");
        paramsData.put("appid", "wxeb1b1558437e9b12");
        //paramsData.put("subscribeAppid", "154344164291514368");
        Object result = SdkUtil.post(paramsData, routeConfigWechatSubmcht);
        return new BaseResponse(CommonErrorCode.SUCCESS,result);
    }

    @ApiOperation(value = "查询微信子商户配置")
    @PostMapping("/configWechatSubmchtQuery")
    public BaseResponse configWechatSubmchtQuery() throws Exception {
        log.info("--------查询微信子商户配置--------");
        Map<String, Object> paramsData = new HashMap<>();

        Object result = SdkUtil.post(paramsData, routeConfigWechatSubmchtQuery);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @PostMapping("/test")
    public void test() throws Exception {
        log.info("--------热刷测试--------");
        log.info("routeQueryRegister:{}",routeQueryRegister);
    }
}
