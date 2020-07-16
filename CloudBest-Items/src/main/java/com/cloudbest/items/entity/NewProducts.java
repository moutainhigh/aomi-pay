package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("new_products")
public class NewProducts implements Serializable {
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

    @TableId(value = "validity_time")
    private LocalDateTime validityTime;//关闭时间（下架时间）

    @TableId(value = "type")
    private Integer type;//类型（1：点击进入商品详情；2：点击进入活动标签；3：点击进入网址）

    @TableId(value = "tag")
    private String tag;//标签（商品ID或者网址地址或者活动标签枚举值）

    @TableId(value = "create_time")
    private LocalDateTime createTime;//创建时间


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getGroudingTime() {
        return groudingTime;
    }

    public void setGroudingTime(LocalDateTime groudingTime) {
        this.groudingTime = groudingTime;
    }

    public LocalDateTime getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(LocalDateTime validityTime) {
        this.validityTime = validityTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "NewProducts{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", groudingTime=" + groudingTime +
                ", validityTime=" + validityTime +
                ", type=" + type +
                ", tag='" + tag + '\'' +
                ", createTime=" + createTime +
                '}';
    }


}
