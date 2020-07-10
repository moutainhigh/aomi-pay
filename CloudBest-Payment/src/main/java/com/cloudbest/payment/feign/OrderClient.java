package com.cloudbest.payment.feign;

import com.cloudbest.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-order")
public interface OrderClient {

    @RequestMapping(value="order/main/updateOrder",method= RequestMethod.POST)
    //传入商品信息
    public Result updateOrder(@RequestParam("orderId") String orderId,@RequestParam("status") Integer status);
}
