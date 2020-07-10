package com.cloudbest.order.controller;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.service.MainService;
import com.cloudbest.order.vo.MainEntityVO;
import com.cloudbest.order.vo.OrderMainVO;
import com.cloudbest.order.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MainService mainService;

    //分页查询用户所有订单（主订单）
    @PostMapping("app/order/main/select/all/{token}")
    public Result selectAllOrder(@RequestBody OrderMainVO vo,@PathVariable("token") String token){
        List<MainEntityVO> list = mainService.selectAllOrder(vo,token);
        return new Result(CommonErrorCode.SUCCESS,list);
    }


    //分页查询所有状态的订单
    @PostMapping("app/order/main/select/status/{token}")
    public Result selectAllStatus(@RequestBody OrderMainVO vo, @PathVariable("token") String token){
        List<MainEntityVO> list = mainService.selectAllStatus(vo,token);
        return new Result(CommonErrorCode.SUCCESS,list);
    }


    //根据主订单号查询主订单
    @GetMapping("order/main/select")
    public Result selectMainOrderById(@RequestParam("id") String id){
        MainEntity main = mainService.selectMainOrderById(id);
        return new Result(CommonErrorCode.SUCCESS,main);
    }


    //修改订单状态
    @PostMapping("/app/order/main/update")
    public Result updateMainOrder(@RequestBody OrderSubmitVO orderSubmitVO){

        try {
            mainService.editMain(orderSubmitVO);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }





    //接口方法
    //支付完成后修改订单
    @PostMapping("order/main/updateOrder")
    public Result updateOrder(@RequestParam("orderId") String orderId,@RequestParam("status") Integer status){
        log.info("支付成功修改订单操作开始,订单号："+orderId+",状态："+status);
        MainEntity mainEntity = new MainEntity();
        try {
            mainEntity = mainService.updateOrderAfterPay(orderId,status);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,mainEntity);
    }



    //逻辑删除
    //根据订单号删除主订单
    @GetMapping("order/main/delete")
    public Result deleteMainOrder(@RequestBody String id){
        mainService.deleteMainOrder(id);
        return new Result(CommonErrorCode.SUCCESS);
    }


    //主表里添加数据
    @PostMapping("main/add")
    public Result createMainOrder(@RequestBody MainEntity mainEntity){
        mainService.createMainOrder(mainEntity);
        return new Result(CommonErrorCode.SUCCESS,mainEntity);//返回插入几条信息
    }

    //根据id查询主表

    @GetMapping("mian/select/{id}")
    public Result selectMainOrder(@PathVariable("id") Integer id){
        MainEntity main = mainService.selectByid(id);
        return new Result(CommonErrorCode.SUCCESS,main);
    }

    //修改主表数据
    @PostMapping("main/off")
    public Result offMainOrder(@RequestBody MainEntity mainEntity){
        mainService.offMain(mainEntity.getId());
        return new Result(CommonErrorCode.SUCCESS);
    }

    //查询主表数据列表
    @PostMapping("main/queryList")
    public Result selectMainOrderList(@RequestBody OrderMainVO vo){
        List<MainEntity> mainEntityList = new ArrayList<>();
        mainEntityList = mainService.queryList(vo);
        return new Result(CommonErrorCode.SUCCESS,mainEntityList);
    }

}



























