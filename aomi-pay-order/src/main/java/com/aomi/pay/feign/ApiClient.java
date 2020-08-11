package com.aomi.pay.feign;

import com.aomi.pay.dto.hx.JsPayDTO;
import com.aomi.pay.vo.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-api")
public interface ApiClient {

    //H5支付
    @PostMapping("/pay/onlineTrade")
    public BaseResponse onlineTrade(@RequestBody JsPayDTO jsPayDTO) throws Exception;

}
