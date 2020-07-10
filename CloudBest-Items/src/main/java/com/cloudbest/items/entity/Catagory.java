package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("c_catagory")
public class Catagory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    @TableId(value = "catagory_name")
    private String catagoryName;

    /**
     * 父级分类ID（父级id为0，代表当前就是最上级）
     */
    @TableId(value = "parent_catagory_id")
    private Integer parentCatagoryId;

    /**
     * 分类状态（0：关闭；1：开启；9：逻辑删除）
     */
    @TableId(value = "status")
    private Integer status;


    /**
     * 最后修改时间
     */
    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableId(value = "img")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public Integer getParentCatagoryId() {
        return parentCatagoryId;
    }

    public void setParentCatagoryId(Integer parentCatagoryId) {
        this.parentCatagoryId = parentCatagoryId;
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
        return "Catagory{" +
                "id=" + id +
                ", catagoryName='" + catagoryName + '\'' +
                ", parentCatagoryId=" + parentCatagoryId +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", img='" + img + '\'' +
                '}';
    }
}
