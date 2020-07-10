package com.cloudbest.order.controller;

import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.service.SecondarilyService;
import com.cloudbest.order.vo.SecondarilyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@CrossOrigin
@RestController
public class SecondarilyController {


    @Autowired
    private SecondarilyService secondarilyService;

    //根据主订单号查询子订单信息
    @PostMapping("order/secondarily/select")
    public Result selectSecondarilyOrderById(@RequestParam("id") String id){
        SecondarilyEntity secondarilyEntity = secondarilyService.selectSecondarilyOrderById(id);
        return new Result(CommonErrorCode.SUCCESS, secondarilyEntity);
    }


    //未完成
    //根据主订单号查询子订单号 根据子订单号查询物流状态
    @GetMapping("order/secondarily/selectid")   //ok
    public Result selectDeliveryStatus(@RequestParam("id") String id){
        List<String> deliveryStatus = secondarilyService.selectDeliveryStatus(id);
        return new Result(CommonErrorCode.SUCCESS, deliveryStatus);
    }

    //表里添加数据
    @PostMapping("secondarily/add")
    public Result createSecondarilyOrder(@RequestBody SecondarilyEntity secondarilyEntity){
        secondarilyService.createSecondarilyOrder(secondarilyEntity);
        return new Result(CommonErrorCode.SUCCESS,secondarilyEntity);
    }

    //根据id查询表
    @GetMapping("secondarily/select/{id}")
    public Result selectSecondarilyOrder(@PathParam("id") Integer id){
        SecondarilyEntity secondarilyEntity = secondarilyService.selectByid(id);
        return new Result(CommonErrorCode.SUCCESS, secondarilyEntity);
    }

    //修改表数据
    @PostMapping("secondarily/update")
    public Result updateSecondarilyOrder(@RequestBody SecondarilyEntity secondarilyEntity){
        secondarilyService.updateSecondarilyOrder(secondarilyEntity);
        return new Result(CommonErrorCode.SUCCESS, secondarilyEntity);
    }

    //根据用户id查询收货信息 收货人，手机号，收货地址
    @PostMapping("secondarily/userinfo")
    public Result selectUserInfoById(@PathParam("id")Long id){
        secondarilyService.selectUserInfoById(id);
        return new Result(CommonErrorCode.SUCCESS);
    }

    //根据订单号删除订单 逻辑删除
    @PostMapping("secondarily/off")
    public Result offMainOrder(@RequestBody SecondarilyEntity secondarilyEntity){
        secondarilyService.offSecondarily(secondarilyEntity.getId());
        return new Result(CommonErrorCode.SUCCESS);
    }

    //查询主表数据列表
    @PostMapping("secondarily/queryList")
    public Result selectMainOrderList(@RequestBody SecondarilyVO vo){
        List<SecondarilyEntity> secondarilyEntityList = new ArrayList<>();
        secondarilyEntityList = secondarilyService.queryList(vo);
        return new Result(CommonErrorCode.SUCCESS,secondarilyEntityList);
    }


}
