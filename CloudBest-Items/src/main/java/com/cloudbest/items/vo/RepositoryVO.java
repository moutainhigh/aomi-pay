package com.cloudbest.items.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RepositoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 所属供应商ID
     */
    private Integer supplierId;

    /**
     * 仓库联系人
     */
    private String admin;

    /**
     * 仓库联系电话
     */
    private String phone;

    /**
     * 发货城市
     */
    private String city;

    /**
     * 仓库详细地址
     */
    private String address;

    /**
     * 仓库状态（0：关闭；1：开启）
     */
    private Integer status;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 运费金额
     */
    private BigDecimal transportAmount;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public BigDecimal getTransportAmount() {
        return transportAmount;
    }

    public void setTransportAmount(BigDecimal transportAmount) {
        this.transportAmount = transportAmount;
    }

    @Override
    public String toString() {
        return "RepositoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplierId=" + supplierId +
                ", admin='" + admin + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", transportAmount=" + transportAmount +
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}
