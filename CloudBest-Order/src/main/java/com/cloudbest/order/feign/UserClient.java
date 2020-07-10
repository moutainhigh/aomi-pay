package com.cloudbest.order.feign;

import com.cloudbest.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-user")//远程调用的服务名
public interface UserClient  {
    //查询用户地址
    @RequestMapping(value = "user/queryAddr",method = RequestMethod.POST)
    public Result queryAddr(@RequestParam("customerId")Long customerId);

    //通过id查询用户信息
    @RequestMapping(method = RequestMethod.POST,value = "user/selectUser")
    public Result selectUserById(@RequestParam("customerId")Long customerId);

}
