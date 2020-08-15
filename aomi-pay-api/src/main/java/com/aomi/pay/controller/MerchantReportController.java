package com.aomi.pay.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    /**
//     * 查询无卡商户报备状态
//     */
//    @Value("${api_route.mcht_report.query_register}")
//    private String routeQueryRegister;
//
//    /**
//     * 微信子商户配置
//     */
//    @Value("${api_route.mcht_report.config_echat_submcht}")
//    private String routeConfigWechatSubmcht;
//
//    /**
//     * 微信子商户配置
//     */
//    @Value("${api_route.mcht_report.config_echat_submcht_query}")
//    private String routeConfigWechatSubmchtQuery;
//
//    /**
//     * 查询微信子商户配置
//     */
//    @Value("${api_route.mcht_report.query_unionpay}")
//    private String routeQueryUnionpay;
//
//    @ApiOperation(value = "查询无卡商户报备状态")
//    @PostMapping("/queryRegister")
//    public BaseResponse queryRegister() throws Exception {
//        log.info("--------查询无卡商户报备状态--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        String result = SdkUtil.post(paramsData, routeQueryRegister);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//
//    @ApiOperation(value = "微信子商户配置")
//    @PostMapping("/configWechatSubmcht")
//    public BaseResponse configWechatSubmcht() throws Exception {
//        log.info("--------微信子商户配置--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        paramsData.put("appid", "154344164291514368");
//        paramsData.put("subscribeAppid", "154344164291514368");
//        String result = SdkUtil.post(paramsData, routeConfigWechatSubmcht);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//
//    @ApiOperation(value = "查询微信子商户配置")
//    @PostMapping("/configWechatSubmchtQuery")
//    public BaseResponse configWechatSubmchtQuery() throws Exception {
//        log.info("--------查询微信子商户配置--------");
//        Map<String, Object> paramsData = new HashMap<>();
//
//        String result = SdkUtil.post(paramsData, routeConfigWechatSubmchtQuery);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//
//    @PostMapping("/test")
//    public void test() throws Exception {
//        log.info("--------热刷测试--------");
//        log.info("routeQueryRegister:{}",routeQueryRegister);
//    }
}
