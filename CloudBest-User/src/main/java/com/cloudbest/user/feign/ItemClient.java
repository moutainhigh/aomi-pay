package com.cloudbest.user.feign;


import com.cloudbest.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient("service-items")
public interface ItemClient {


    //查询库存信息
    @GetMapping("item/query/skuid/stock")
    public Result selecStockBySkuId(@RequestParam("id") Integer id);

    //查询图片信息
    @GetMapping("item/query/skuid/img")
    public Result selecImgBySkuId(@RequestParam("id") Integer id);

    /**
     * 上传图片
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/add/uploadImg")
    public Result uploadImg(HttpServletRequest request);
    //根据spuid查询商品
    @RequestMapping(method = RequestMethod.POST, value = "/items/user/query/getItemInfoById")
    public Result getItemInfoById(@RequestParam("id") Integer itemId);
}
