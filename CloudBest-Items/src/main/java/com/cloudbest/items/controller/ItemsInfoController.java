package com.cloudbest.items.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Promotion;
import com.cloudbest.items.service.ItemsInfoService;
import com.cloudbest.items.service.PromotionService;
import com.cloudbest.items.vo.ItemsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 商品详情表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
//实现跨域注解
//origin="*"代表所有域名都可访问
//maxAge飞行前响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
//若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class ItemsInfoController {

    @Autowired
    private ItemsInfoService cItemsInfoService;

    @Autowired
    private PromotionService promotionService;


    /**
     * 添加商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/add/insertItems")
    public Result insertItems(HttpServletRequest request,@RequestBody ItemsInfo info){
        ItemsInfo itemsInfo = new ItemsInfo();

        try{
            itemsInfo = cItemsInfoService.createNewItem(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }

        return new Result(CommonErrorCode.SUCCESS,itemsInfo);
    }

    /**
     * 添加商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/add/insertItemsTest")
    public Result insertItemsTest(HttpServletRequest request,
                                  @RequestParam(value = "name", required = true) String name,
                                  @RequestParam(value = "barCode", required = false) String barCode,
                                  @RequestParam(value = "firstCategoryId", required = true) int firstCategoryId,
                                  @RequestParam(value = "secondCategoryId", required = true) int secondCategoryId,
                                  @RequestParam(value = "thirdCategoryId", required = true) int thirdCategoryId,
                                  @RequestParam(value = "supplierId", required = false) int supplierId,
                                  @RequestParam(value = "groudingTime", required = true) Date groudingTime,
                                  @RequestParam(value = "validityTime", required = true) Date validityTime,
                                  @RequestParam(value = "description", required = false) String description
                                  ){
        ItemsInfo itemsInfo = new ItemsInfo();
        itemsInfo.setName(name);
        itemsInfo.setBarCode(barCode);
        itemsInfo.setFirstCategoryId(firstCategoryId);
        itemsInfo.setSecondCategoryId(secondCategoryId);
        itemsInfo.setThirdCategoryId(thirdCategoryId);
        itemsInfo.setSupplierId(supplierId);
        itemsInfo.setGroudingTime(groudingTime);
        itemsInfo.setValidityTime(validityTime);
        itemsInfo.setDescription(description);
        itemsInfo.setCreateTime(com.cloudbest.common.util.DateUtil.getCurrDate());
        itemsInfo.setUpdateTime(com.cloudbest.common.util.DateUtil.getCurrDate());
        try{
            itemsInfo = cItemsInfoService.createNewItem(itemsInfo);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }

        return new Result(CommonErrorCode.SUCCESS,itemsInfo);
    }

    /**
     * 修改商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/update/updateItems")
    public Result updateItems(HttpServletRequest request,@RequestBody ItemsInfo info){
        ItemsInfo itemsInfo = new ItemsInfo();
        try{
            itemsInfo = cItemsInfoService.updateItems(info);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsInfo);
    }


    /**
     * 删除商品信息（物理删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/delete/deleteItems")
    public Result deleteItems(HttpServletRequest request,@RequestParam(value = "itemId", required = true) Integer itemId){

        try{
            cItemsInfoService.deleteItems(itemId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除商品信息（逻辑删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/delete/offItems")
    public Result offItems(HttpServletRequest request,@RequestBody ItemsInfoVO vo){

        try{
            cItemsInfoService.offItems(vo.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 上下架商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/onItems")
    public Result onOffItems(HttpServletRequest request,@RequestBody ItemsInfoVO vo){

        try{
            cItemsInfoService.onOffItems(vo.getSpuId(),vo.getIsView());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/queryItems")
    public Result queryItems(HttpServletRequest request,@RequestBody ItemsInfoVO vo){

        List<Map<String,Object>> mapList = new ArrayList<>();
        List<Map<String,Object>> mapPromotionList = new ArrayList<>();
        Map<String,Object> mapResult = new HashMap<>();
        try{
            mapList = cItemsInfoService.queryItems(vo);

            //查询活动信息
            Promotion promotion = promotionService.queryPromotionFirst();
            if (null!=promotion){
                mapPromotionList = promotionService.queryPromotionItems(promotion.getSpuIds());
                mapResult.put("itemPromotionList",mapPromotionList);
                mapResult.put("itemList",mapList);
                mapResult.put("promotionId",promotion.getId());
                mapResult.put("promotionName",promotion.getName());
                mapResult.put("promotionStartTime",promotion.getStartTime());
                mapResult.put("promotionEndTime",promotion.getEndTime());
                mapResult.put("promotionStatus",promotion.getStatus());
                mapResult.put("countdown", DateUtil.betweenMs(com.cloudbest.common.util.DateUtil.getCurrDate(),promotion.getEndTime()));
            }else {
                mapResult.put("itemList",mapList);
                mapResult.put("promotionStatus",0);
            }
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapResult);
    }

    /**
     * 查询商品信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/items/query/queryItemsByName")
    public Result queryItemsByName(HttpServletRequest request,
                             @RequestBody JSONObject str){
        String name = str.getString("name");
        Integer itemId = str.getInteger("itemId");
        List<ItemsInfo> itemsInfos = new ArrayList<>();
        try{
            itemsInfos = cItemsInfoService.queryItemsByName(itemId,name);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsInfos);
    }
    /**
     * 查询商品信息
     * zpc
     */

    @RequestMapping(method = RequestMethod.POST, value ="/items/query/queryItemsById")
    public Result queryItemsById(@RequestParam Integer spuId){

        ItemsInfo itemsInfo = null;
        try{
            itemsInfo = cItemsInfoService.queryItemsById(spuId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsInfo);
    }




    /**
     * 查询商品信息(猜你喜欢，随机查询数据)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/queryFavoriteItems")
    public Result queryFavoriteItems(HttpServletRequest request
//                                     @PathVariable("token") String token
    ){
//        long userid;
//        try {
//            userid = TokenUtil.getUserId(token);
//        } catch (Exception e) {
//            return new Result(CommonErrorCode.E_900121);
//        }
        List<Map<String,Object>> mapList = new ArrayList<>();
        try{
            mapList = cItemsInfoService.queryFavoriteItems();
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapList);
    }


    /**
     * 精品推荐（写死数据）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/queryTopQualityItems")
    public Result queryTopQualityItems(HttpServletRequest request
    ){

        List<Map<String,Object>> mapList = new ArrayList<>();
        try{
            mapList = cItemsInfoService.queryTopQualityItems();
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapList);
    }

    /**
     * 猜你喜欢，随机查询数据+精品推荐（写死数据）
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/queryIndexItems")
    public Result queryIndexItems(HttpServletRequest request
    ){

        Map<String,Object> map = new HashMap<>();
        try{
            map.put("topQualityItems",cItemsInfoService.queryTopQualityItems());
            map.put("favoriteItems",cItemsInfoService.queryFavoriteItems());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,map);
    }

    //查询商品详情信息（包括sku，sku对应的价格，描述，图片，sku销量，仓库所在地，发货城市，用户默认地址）
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/getItemInfo")
    public Result getItemInfo(HttpServletRequest request,
//                              @PathVariable("token") String token,
                              @RequestBody JSONObject str
                              ){
//        long userid;
//        try {
//            userid = TokenUtil.getUserId(token);
//        } catch (Exception e) {
//            return new Result(CommonErrorCode.E_900121);
//        }
        Integer itemId = str.getInteger("itemId");
        if (null==itemId){
            return new Result(999999,"商品ID不能为空",false);
        }
        Map<String,Object> mapResult = new HashMap<>();
        try{
            mapResult = cItemsInfoService.getItemInfo(itemId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapResult);
    }


    //用户收藏品查询
    @RequestMapping(method = RequestMethod.POST, value = "/items/user/query/getItemInfoById")
    public Result getItemInfoById(@RequestParam("id") Integer itemId){
        ItemsInfoVO info = new ItemsInfoVO();
        try{
            info = cItemsInfoService.getItemInfoById(itemId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,info);
    }

    //通过skuId，商品ID查询出商品的对应价格以及图片
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/getItemInfoSku")
    public Result getItemInfoSku(HttpServletRequest request,
                                 @RequestBody JSONObject str
    ){
        String skuId = str.getString("skuId");
        String itemId = str.getString("itemId");
        if (null==skuId||null==itemId){
            return new Result(999999,"参数不匹配",false);
        }
        Map<String,Object> mapResult = new HashMap<>();
        try{
            mapResult = cItemsInfoService.getItemInfoSku(Integer.valueOf(skuId),Integer.valueOf(itemId));
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapResult);
    }

    //根据商品id+skuId+商品数量计算总价(用于前端页面动态刷新)
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/items/query/totalItemsPrice")
    public Result totalItemsPrice(HttpServletRequest request,
//                                  @PathVariable("token") String token,
                                  @RequestBody JSONObject str
    ){
//        long userid;
//        try {
//            userid = TokenUtil.getUserId(token);
//        } catch (Exception e) {
//            return new Result(CommonErrorCode.E_900121);
//        }
        Map<String, BigDecimal> resultMap = new HashMap<>();
        String skuId = str.getString("skuId");
        String count = str.getString("count");
        String itemId = str.getString("itemId");
        if (null==skuId||null==itemId||null==count){
            return new Result(999999,"参数不匹配",false);
        }
        try{
            resultMap = cItemsInfoService.totalItemsPrice(Integer.valueOf(skuId),Integer.valueOf(itemId),Integer.valueOf(count));
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,resultMap);
    }

}
