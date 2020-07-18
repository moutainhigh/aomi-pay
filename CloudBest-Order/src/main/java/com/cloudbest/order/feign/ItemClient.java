package com.cloudbest.order.feign;

import com.cloudbest.common.domain.Result;
import com.cloudbest.order.vo.PurchaseLimitVO;
import com.cloudbest.order.vo.SkuLockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("service-items")
public interface ItemClient {

    //查询库存信息  和商品信息
    @GetMapping("/item/query/skuid/stock")
    public Result selecStockBySkuId(@RequestParam("id") Integer id);

    //查询图片信息
    @RequestMapping(method = RequestMethod.POST,value = "/items/query/skuid/img")
    public Result selecImgBySkuId(@RequestParam("id") Integer id);

    @GetMapping("/items/check/unlock")
    public Result unLockStock(@RequestParam("orderToken")String orderToken);
    //锁商品SKU方法
    @PostMapping("/items/check/lock")
    public Result checkAndLockStock(@RequestBody(required = true) List<SkuLockVO> skuLockVOS);
    //修改商品销量
    @GetMapping("item/query/skuid/updateSaleVolume")
    public Result updateSaleVolume(@RequestParam("id") Integer id,@RequestParam("num") Integer num);

    @RequestMapping(method = RequestMethod.POST, value ="/items/query/queryItemsById")
    public Result queryItemsById(@RequestParam Integer spuId);

    @PostMapping("/items/purchaseLimit/getByItemIdSkuId")
    public Result getByItemIdSkuId(@RequestBody PurchaseLimitVO purchaseLimitVO);
}
