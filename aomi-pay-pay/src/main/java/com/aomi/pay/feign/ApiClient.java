package com.aomi.pay.feign;

import com.aomi.pay.dto.hx.JsPayDTO;
import com.aomi.pay.vo.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 调环迅api接口client
 *
 * @author : hdq
 * @date 2020/8/13
 */
@FeignClient("service-api")
public interface ApiClient {

    /**
     * @author  hdq
     * @date  2020/8/13
     * @desc H5支付
     **/
    @PostMapping("/pay/onlineTrade")
    public BaseResponse onlineTrade(@RequestBody JsPayDTO jsPayDTO) throws Exception;

}
