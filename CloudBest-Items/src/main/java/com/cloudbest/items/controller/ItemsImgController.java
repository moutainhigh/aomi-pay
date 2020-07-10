package com.cloudbest.items.controller;


import com.alibaba.fastjson.JSONObject;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.AliOSSUtil;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.items.entity.Catagory;
import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.service.ItemsImgService;
import com.cloudbest.items.vo.ItemsImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品图片表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class ItemsImgController {

    @Autowired
    private ItemsImgService cItemsImgService;


    /**
     * 根据商品sku_id查询图片信息
     * @return
     */

    @RequestMapping(method = RequestMethod.POST,value = "/items/query/skuid/img")
    public Result selecImgBySkuId(@RequestParam("id") Integer id){
        ItemsImg cItemsImg = cItemsImgService.selecImgBySkuId(id);
        return new Result(CommonErrorCode.SUCCESS,cItemsImg);
    }



    /**
     * 添加商品图片信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/add/insertItemsImgTest")
    public Result insertItemsImg(HttpServletRequest request,
            @RequestParam(value = "desc") String desc,
            @RequestParam(value = "itemId") Integer itemId,
            @RequestParam(value = "skuId") Integer skuId ){
        ItemsImg itemsImg = new ItemsImg();
        try{
            itemsImg = cItemsImgService.insertItemsImg(request,desc,itemId,skuId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsImg);
    }

    /**
     * 添加商品图片信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/add/insertItemsImg")
    public Result insertItemsImg(HttpServletRequest request,
                                 @RequestBody ItemsImg img
//                                 @RequestParam(value = "desc", required = false) String desc,
//                                 @RequestParam(value = "itemId", required = true) Integer itemId,
//                                 @RequestParam(value = "skuId", required = false) Integer skuId,
//                                 @RequestParam(value = "imgUrl", required = true) String imgUrl,
//                                 @RequestParam(value = "sort", required = false) Integer sort
                                 ){
        List<ItemsImg> imgList = new ArrayList<>();
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setSkuId(img.getSkuId());
        itemsImg.setImgDesc(img.getImgDesc());
        itemsImg.setImgUrl(img.getImgUrl());
        itemsImg.setItemId(img.getItemId());
        itemsImg.setSort(img.getSort());
        itemsImg.setStatus(1);
        itemsImg.setUpdateTime(DateUtil.getCurrDate());
        try{
            imgList = cItemsImgService.createNewItemsImg(itemsImg);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,imgList);
    }

    /**
     * 添加商品图片(上传图片)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/add/uploadImg")
    public Result uploadImg(HttpServletRequest request
    ){
        String imgurls="";
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("image");
            if(files==null||files.size()==0){
                throw new BusinessException(CommonErrorCode.E_901007.getCode(),"请上传至少一张图片");
            }

            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
            String ossObjectName = "";

            int i=1;

            if(files!=null&&files.size()>0){
                for (MultipartFile file : files) {

                    String fileName = file.getOriginalFilename();
                    String prefix=fileName.substring(fileName.lastIndexOf("."));
                    fileName = System.currentTimeMillis()+i+prefix;

                    ossObjectName = ossObjectNamePrefix + fileName;
                    AliOSSUtil aliOSSUtil = new AliOSSUtil();
                    try {
                        aliOSSUtil.uploadStreamToOss(ossObjectName,file.getInputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
                    if (i==1){
                        imgurls = imgurls + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }else {
                        imgurls = imgurls + "," + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }
                    i++;
                }
            }
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,imgurls);
    }

    /**
     * 修改商品图片信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/update/updateItemsImg")
    public Result updateItemsImg(HttpServletRequest request,@RequestBody ItemsImg info){
        ItemsImg itemsImg = new ItemsImg();
        try{
            itemsImg = cItemsImgService.updateItemsImg(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsImg);
    }

    /**
     * 删除商品图片信息（物理删除，暂无用）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/delete/deleteItemsImg")
    public Result deleteItemsImg(HttpServletRequest request,@RequestParam(value = "itemsImgId", required = true) Integer itemsImgId){
        try{
            cItemsImgService.deleteItemsImg(itemsImgId);

        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除商品图片信息（逻辑删除，暂无用）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/delete/offItemsImg")
    public Result offItemsImg(HttpServletRequest request,@RequestBody ItemsImg info){
        try{
            cItemsImgService.offItemsImg(info.getId());
        }catch (BusinessException businessException){

            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询商品图片信息(列表)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/query/queryItemsImg")
    public Result queryItemsImg(HttpServletRequest request,
                                @RequestBody ItemsImgVO img){
        List<ItemsImg> itemsImgList = new ArrayList<>();
        try{
            itemsImgList = cItemsImgService.queryItemsImg(img);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsImgList);
    }

    /**
     * 查询商品图片信息(详情)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/itemsImg/query/queryById")
    public Result queryById(HttpServletRequest request,
                            @RequestBody JSONObject str
    ){
        String id = str.getString("id");
        if (id==null){
            return new Result(CommonErrorCode.E_901002);
        }
        ItemsImg itemsImg = new ItemsImg();
        try{
            itemsImg = cItemsImgService.queryById(Integer.valueOf(id));
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,itemsImg);
    }
}
