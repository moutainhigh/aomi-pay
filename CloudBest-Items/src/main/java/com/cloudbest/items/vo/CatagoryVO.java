package com.cloudbest.items.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cloudbest.items.entity.Catagory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

public class CatagoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 分类名称
     */
    private String catagoryName;

    /**
     * 父级分类ID（父级id为0，代表当前就是最上级）
     */
    private Integer parentCatagoryId;

    /**
     * 分类状态（0：关闭；1：开启）
     */
    private Integer status;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    private List<Catagory> catagoryList;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public List<Catagory> getCatagoryList() {
        return catagoryList;
    }

    public void setCatagoryList(List<Catagory> catagoryList) {
        this.catagoryList = catagoryList;
    }

    @Override
    public String toString() {
        return "CatagoryVO{" +
                "id=" + id +
                ", catagoryName='" + catagoryName + '\'' +
                ", parentCatagoryId=" + parentCatagoryId +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", catagoryList=" + catagoryList +
                ", current=" + current +
                ", size=" + size +
                ", img='" + img + '\'' +
                '}';
    }
}
