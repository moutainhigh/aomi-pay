package com.cloudbest.items.controller;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.entity.Stock;
import com.cloudbest.items.service.StockService;
import com.cloudbest.items.vo.ItemsStockVO;
import com.cloudbest.items.vo.SkuLockVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品SKU表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class StockController {

    @Autowired
    private StockService cStockService;

    //解锁库存
    @GetMapping("/items/check/unlock")
    public Result unLockStock(@RequestParam("orderToken")String orderToken){
        this.cStockService.unLockStock(orderToken);
        System.out.println("执行成功");
        return null;
    }

   //锁商品SKU方法  验库存锁库存
    @PostMapping("/items/check/lock")
    public Result checkAndLockStock(@RequestBody(required = true) List<SkuLockVO> skuLockVOS){

        String msg = null;
        try {
            msg = this.cStockService.checkAndLockStock(skuLockVOS);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }

        if (StringUtils.isNotBlank(msg)) {
            return new Result(CommonErrorCode.FAIL);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /*
        try{
            catagory = cCatagoryService.createNewCatagory(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
    */


    //测试验商品SKU方法方法
    @GetMapping("items/testone")
    public Result test(Integer id,Integer num){
        cStockService.test(id,num);
        return null;
    }

    //测试锁商品SKU方法方法
    @GetMapping("items/testtwo")
    public Result test2(Integer id,Integer num){
        cStockService.test2(id,num);
        return null;
    }



    /**
     * 添加商品SKU信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/add/insertStock")
    public Result insertStock(HttpServletRequest request, @RequestBody Stock info){
        Stock stock = new Stock();

        try{
            stock = cStockService.createNewStock(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,stock);
    }

    /**
     * 修改商品SKU信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/update/updateStock")
    public Result updateStock(HttpServletRequest request,@RequestBody Stock info){
        Stock stock = new Stock();
        try{
            stock = cStockService.updateStock(info);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,stock);
    }

    /**
     * 删除商品SKU信息（物理删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/delete/deleteStock")
    public Result deleteStock(HttpServletRequest request,@RequestParam(value = "stockId", required = true) Integer stockId){

        try{
            cStockService.deleteStock(stockId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除商品信息（逻辑删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/delete/offStock")
    public Result offStock(HttpServletRequest request,@RequestBody ItemsStockVO vo){

        try{
            cStockService.offStock(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 上架商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/onStock")
    public Result onStock(HttpServletRequest request,@RequestBody ItemsStockVO vo){

        try{
            cStockService.onStock(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 上下架商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/onOffStock")
    public Result onOffStock(HttpServletRequest request,@RequestBody ItemsStockVO vo){

        try{
            cStockService.onOffStock(vo.getSkuId(),vo.getStatus());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询商品SKU信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/query/queryStock")
    public Result queryStock(HttpServletRequest request,@RequestBody ItemsStockVO vo){
        List<Stock> stockList = new ArrayList<>();
        try{
            stockList = cStockService.queryStock(vo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,stockList);
    }


    /**
     根据订商品sku_id查询库存信息 和 商品信息

     */
    @GetMapping("/item/query/skuid/stock")
    public Result selecStockBySkuId(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        map = cStockService.selecStockBySkuId(id);
        return new Result(CommonErrorCode.SUCCESS,map);
    }

    /**
     * 根商品skuId变动商品销量
     */
    @GetMapping("item/query/skuid/updateSaleVolume")
    public Result updateSaleVolume(@RequestParam("id") Integer id,@RequestParam("num") Integer num){
        Stock cStock = new Stock();
        try{
            cStock = cStockService.updateSaleVolume(id,num);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,cStock);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/items/stock/query/queryById")
    public Result queryById(@RequestBody ItemsStockVO vo){
        Map<String,Object> map = new HashMap<>();
        map = cStockService.selecStockBySkuId(vo.getId());
        return new Result(CommonErrorCode.SUCCESS,map);
    }

}
