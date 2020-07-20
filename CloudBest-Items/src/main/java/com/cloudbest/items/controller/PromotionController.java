package com.cloudbest.items.controller;

import com.cloudbest.items.service.ItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品详情表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class PromotionController {

    @Autowired
    private ItemsInfoService cItemsInfoService;

    //查询显示抢购的商品


}
