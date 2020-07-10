package com.cloudbest.order.controller;


import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.order.entity.ItemEntity;
import com.cloudbest.order.service.ItemService;
import com.cloudbest.order.vo.ItemVO;
import com.cloudbest.order.vo.MainEntityVO;
import com.cloudbest.order.vo.OrderMainVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单项信息 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

@Slf4j
@CrossOrigin
@RestController
//@RequestMapping("order")
public class ItemController {

    @Autowired
    private ItemService itemService;



    //根据订单号查看订单详情（前台）
    @PostMapping("app/order/item/select/{token}")   //ok
    public Result secltItemsById(@RequestBody OrderMainVO vo, @PathVariable("token") String token){
        MainEntityVO entityVO = this.itemService.secltItemsById(vo.getMainOrderId(),token);
       return new Result(CommonErrorCode.SUCCESS,entityVO);
    }


    //查询
    @GetMapping("order/item/select/{id}")
    public Result findByid(@PathParam(value = "id") Integer id){
        ItemEntity itemEntity = this.itemService.findById(id);
        return new Result(CommonErrorCode.SUCCESS,itemEntity);
    }


    //主表里添加数据
    @PostMapping("order/item/add")
    public Result createItemOrder(@RequestBody ItemEntity itemEntity){

        itemService.createItemOrder(itemEntity);
        return new Result(CommonErrorCode.SUCCESS,itemEntity);
    }


    //修改表数据
    @PostMapping("order/item/upd")
    public Result updateItemOrder(@RequestBody ItemEntity itemEntity){
        itemService.updateItemOrder(itemEntity);
        return new Result(CommonErrorCode.SUCCESS,itemEntity);
    }

    //查询表数据列表
    @PostMapping("order/item/queryList")
    public Result selectMainOrderList(@RequestBody ItemVO vo){
        List<ItemEntity> itemEntityList = new ArrayList<>();
        itemEntityList = itemService.queryList(vo);
        return new Result(CommonErrorCode.SUCCESS,itemEntityList);
    }
}





















