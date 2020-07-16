package com.cloudbest.items.controller;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.mapper.AppAdvertiseMapper;
import com.cloudbest.items.mapper.NewProductsMapper;
import com.cloudbest.items.mapper.RotationChartMapper;
import com.cloudbest.items.service.NewAndRotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class NewAndRotationController {
    @Autowired
    private NewAndRotationService newAndRotationService;
    @Autowired
    private NewProductsMapper newProductsMapper;
    @Autowired
    private AppAdvertiseMapper appAdvertiseMapper;
    @Autowired
    private RotationChartMapper rotationChartMapper;
    //根据id查询新增商品  value = "/items/query/skuid/img"
    @RequestMapping(method = RequestMethod.POST,value = "/app/items/query/newProduct")
    public Result selectNewProductsById(){
        Map<String,Object> mapResult = null;
        try {
            mapResult = this.newAndRotationService.selectNewProductsById();
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mapResult);
    }

//    @PostMapping("items/test/one")
//    public List<NewProducts> test1(){
//        List<NewProducts> newProducts = this.newProductsMapper.selectNewProducts();
//        return newProducts;
//    }
//
//    @PostMapping("items/test/two")
//    public List<AppAdvertise> test2(){
//        List<AppAdvertise> appAdvertises = this.appAdvertiseMapper.selectAppAdvertises();
//        return appAdvertises;
//    }
//
//    @PostMapping("items/test/three")
//    public List<RotationChart> test3(){
//        List<RotationChart> rotationCharts = this.rotationChartMapper.selectRotationCharts();
//        return  rotationCharts;
//    }

//    //添加新增商品
//    @PostMapping(value = "/items/add/newProduct")
//    public Result addNewProductsById(){
//        this.newAndRotationService.addNewProductsById();
//        return new Result(CommonErrorCode.SUCCESS);
//    }
    //新增轮播图
    //新增广告
}
