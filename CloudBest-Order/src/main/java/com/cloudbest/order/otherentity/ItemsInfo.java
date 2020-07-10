package com.cloudbest.order.otherentity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItemsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "name")
    private String name;

    /**
     * 国条码
     */
    @TableId(value = "bar_code")
    private String barCode;

    /**
     * 一级分类ID
     */
    @TableId(value = "first_category_id")
    private Integer firstCategoryId;

    /**
     * 二级分类ID
     */
    @TableId(value = "second_category_id")
    private Integer secondCategoryId;

    /**
     * 三级分类ID
     */
    @TableId(value = "third_category_id")
    private Integer thirdCategoryId;

    /**
     * 供应商ID
     */
    @TableId(value = "supplier_id")
    private Integer supplierId;

    /**
     * 商品上架时间
     */
    @TableId(value = "grouding_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date groudingTime;

    /**
     * 商品下架时间
     */
    @TableId(value = "validity_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityTime;

    /**
     * 前端是否显示（0：不显示；1：显示）
     */
    @TableId(value = "is_view")
    private Integer isView;

    /**
     * 商品描述
     */
    @TableId(value = "description")
    private String description;

    /**
     * 录入日期
     */
    @TableId(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * spu销量
     */
    @TableId(value = "spu_salt_volume")
    private Integer spuSaltVolume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Integer firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Integer getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Integer secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Integer getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Integer thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Date getGroudingTime() {
        return groudingTime;
    }

    public void setGroudingTime(Date groudingTime) {
        this.groudingTime = groudingTime;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSpuSaltVolume() {
        return spuSaltVolume;
    }

    public void setSpuSaltVolume(Integer spuSaltVolume) {
        this.spuSaltVolume = spuSaltVolume;
    }

    @Override
    public String toString() {
        return "ItemsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", firstCategoryId=" + firstCategoryId +
                ", secondCategoryId=" + secondCategoryId +
                ", thirdCategoryId=" + thirdCategoryId +
                ", supplierId=" + supplierId +
                ", groudingTime=" + groudingTime +
                ", validityTime=" + validityTime +
                ", isView=" + isView +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", spuSaltVolume=" + spuSaltVolume +
                '}';
    }
}
