package com.aomi.pay.feign;

import com.aomi.pay.model.GetSimpleNameRequest;
import com.aomi.pay.vo.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调pay client
 *
 * @author : hdq
 * @date 2020/8/13
 */
@FeignClient("service-pay")
public interface PayClient {

    /**
     * @author  hdq
     * @date  2020/8/13
     * @desc 获取支付宝userId
     **/
    @GetMapping("/aliPay/getUserId")
    public BaseResponse getUserId(@RequestParam String authCode) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/14
     * @desc 获取微信openId
     **/
    @GetMapping("/wxPay/getOpenId")
    public BaseResponse getOpenId(@RequestParam String code) throws Exception;

    /**
     * @author hdq
     * @date 2020/8/18
     * @desc 根据固码获取商户简称
     **/
    @GetMapping("/merchant/getSimpleName")
    public BaseResponse getSimpleName(@RequestBody GetSimpleNameRequest req);

}
