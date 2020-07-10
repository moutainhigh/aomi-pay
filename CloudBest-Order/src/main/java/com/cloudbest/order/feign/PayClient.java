package com.cloudbest.order.feign;

import com.cloudbest.common.domain.Result;
import com.cloudbest.order.vo.AlipayBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-payment")
public interface PayClient {

    @RequestMapping(value="/payment/zfb",method= RequestMethod.POST)
    public Result zfb(@RequestBody AlipayBean alipayBean);
}
