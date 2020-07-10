package com.cloudbest.items.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

public class ItemsImgVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 商品ID
     */
    private Integer itemId;

    /**
     * 商品详细属性ID
     */
    private Integer skuId;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 图片描述
     */
    private String imgDesc;

    /**
     * 图片权重
     */
    private Integer sort;

    /**
     * 图片状态（0：关闭；1：开启）
     */
    private Integer status;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ItemsImgVO{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", skuId=" + skuId +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}
