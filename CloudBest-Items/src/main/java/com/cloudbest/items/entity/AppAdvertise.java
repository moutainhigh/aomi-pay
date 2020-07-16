package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("app_advertise")
public class AppAdvertise {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//

    @TableId(value = "image")
    private String image;//轮播图url

    @TableId(value = "description")
    private String description;//轮播图简介

    @TableId(value = "status")
    private Integer status;//状态（0：关闭；1：开启）

    @TableId(value = "grouding_time")
    private LocalDateTime groudingTime;//开启时间（上架时间）
    //position
    @TableId(value = "position")
    private String position;//开启时间（上架时间）

    @TableId(value = "validity_time")
    private LocalDateTime validityTime;//关闭时间（下架时间）

    @TableId(value = "type")
    private Integer type;//类型（1：点击进入商品详情；2：点击进入活动标签；3：点击进入网址）

    @TableId(value = "tag")
    private String tag;//标签（商品ID或者网址地址或者活动标签枚举值）

    @TableId(value = "create_time")
    private LocalDateTime createTime;//创建时间

}
