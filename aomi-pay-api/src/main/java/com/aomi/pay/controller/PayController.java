package com.aomi.pay.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RefreshScope
@Api(value = "PayController", tags = "交易类(无卡类)接口管理")
@RequestMapping("/pay")
public class PayController {

//    /**
//     * 扫码支付
//     */
//    @Value("${api_route.pay.online_trade}")
//    private String routeOnlineTrade;
//
//    /**
//     * 查询订单
//     */
//    @Value("${api_route.pay.online_query}")
//    private String routeOnlineQuery;
//
//
//    @ApiOperation(value = "h5支付")
//    @PostMapping("/onlineTrade")
//    public BaseResponse onlineTrade() throws Exception {
//        log.info("--------h5支付--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        //paramsData.put("instId", "015001");
//        //paramsData.put("mchtNo", "015370109123528");
//        //paramsData.put("serviceId", "hx.alipay.jspay");//支付宝
//        paramsData.put("serviceId", "hx.wechat.jspay");//微信
//        paramsData.put("version", "1.0.0");
//        paramsData.put("isvOrgId", "015001");
//        paramsData.put("productCode", "100010");//支付宝
//        //paramsData.put("productCode", "100011");//微信
//        paramsData.put("settleType", "DREAL");
//        paramsData.put("outTradeNo", "16512315615615132125");
//        paramsData.put("merchantNo", "015440309175982");
//        paramsData.put("subject", "测试");
//        paramsData.put("amount", "1");
//        paramsData.put("userId", "2021001168632988");
//        //paramsData.put("userId", "oXpzSv9AcnTkxsErnGUKCDzZIZBs");
//        //paramsData.put("subAppid", "wx57f9d11132fc79c1");
//        paramsData.put("notifyUrl", "192.168.103.250:8179/order/payment/notify");
//
//        String result = SdkUtil.post(paramsData, routeOnlineTrade);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//
//    @ApiOperation(value = "订单查询")
//    @PostMapping("/onlineQuery")
//    public BaseResponse onlineQuery() throws Exception {
//        log.info("--------订单查询--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        //paramsData.put("instId", "015001");
//        //paramsData.put("mchtNo", "015370109123528");
//        paramsData.put("serviceId", "hx.unified.query");
//        paramsData.put("version", "1.0.0");
//        paramsData.put("isvOrgId", "015001");
//        paramsData.put("tradeType", "31");
//        //paramsData.put("productCode", "100010");
//        //paramsData.put("settleType", "DREAL");
//        paramsData.put("outTradeNo", "16512315615615132121");
//        paramsData.put("merchantNo", "015370109123528");
//
//        String result = SdkUtil.post(paramsData, routeOnlineTrade);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

}
