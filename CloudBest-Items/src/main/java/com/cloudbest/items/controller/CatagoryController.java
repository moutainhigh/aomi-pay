package com.cloudbest.items.controller;


import com.alibaba.fastjson.JSONObject;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.items.entity.Catagory;
import com.cloudbest.items.service.CatagoryService;
import com.cloudbest.items.vo.CatagoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class CatagoryController {

    @Autowired
    private CatagoryService cCatagoryService;

    /**
     * 添加商品分类
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/add/insertCatagory")
    public Result insertCatagory(HttpServletRequest request, @RequestBody Catagory info){
        Catagory catagory = new Catagory();

        try{
            catagory = cCatagoryService.createNewCatagory(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagory);
    }

    /**
     * 添加商品分类
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/add/insertCatagoryTest")
    public Result insertCatagoryTest(HttpServletRequest request,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "parentCatagoryId", defaultValue= "0" ,required = false) int parentCatagoryId
    ){
        Catagory catagory = new Catagory();
        catagory.setCatagoryName(name);
        catagory.setParentCatagoryId(parentCatagoryId);
        catagory.setStatus(1);
        catagory.setUpdateTime(DateUtil.getCurrDate());
        try{
            catagory = cCatagoryService.createNewCatagory(catagory);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagory);
    }

    /**
     * 修改商品分类信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/update/updateCatagory")
    public Result updateCatagory(HttpServletRequest request,@RequestBody Catagory info){
        Catagory catagoryInfo = new Catagory();
        try{
            catagoryInfo = cCatagoryService.updateCatagory(info);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagoryInfo);
    }


    /**
     * 删除商品分类信息（逻辑删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/delete/offCatagory")
    public Result offCatagory(HttpServletRequest request,@RequestBody Catagory info){
        try{
            cCatagoryService.offCatagory(info.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除商品分类信息（物理删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/delete/deleteCatagory")
    public Result deleteCatagory(HttpServletRequest request,@RequestParam(value = "catagoryId", required = true) Integer catagoryId){

        try{
            cCatagoryService.deleteCatagory(catagoryId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询商品分类信息（前端）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/query/queryById")
    public Result queryById(HttpServletRequest request,
                                 @RequestBody JSONObject str
    ){
        String id = str.getString("id");
        if (id==null){
            return new Result(CommonErrorCode.E_901002);
        }
        Catagory catagory = new Catagory();
        try{
            catagory = cCatagoryService.queryById(Integer.valueOf(id));
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagory);
    }

    /**
     * 查询商品分类信息（前端）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/app/items/catagory/query/queryCatagory")
    public Result queryCatagorys(HttpServletRequest request,
                                 @RequestBody JSONObject str
    ){
        String parentCatagoryId = str.getString("catagoryId");
        if (parentCatagoryId==null){
            parentCatagoryId = "0";
        }
        List<CatagoryVO> catagoryList = new ArrayList<>();
        try{
            catagoryList = cCatagoryService.queryCatagory(Integer.valueOf(parentCatagoryId));
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagoryList);
    }

    /**
     * 查询商品分类信息(后台)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/catagory/query/queryCatagoryList")
    public Result queryCatagoryList(HttpServletRequest request,@RequestBody CatagoryVO info){
        List<Catagory> catagoryList = new ArrayList<>();
        try{
            catagoryList = cCatagoryService.queryCatagoryList(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,catagoryList);
    }
}
