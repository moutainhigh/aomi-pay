package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("c_repository")
public class Repository implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 仓库名称
     */
    @TableId(value = "name")
    private String name;

    /**
     * 所属供应商ID
     */
    @TableId(value = "supplier_id")
    private Integer supplierId;

    /**
     * 仓库联系人
     */
    @TableId(value = "admin")
    private String admin;

    /**
     * 仓库联系电话
     */
    @TableId(value = "phone")
    private String phone;

    /**
     * 发货城市
     */
    @TableId(value = "city")
    private String city;

    /**
     * 仓库详细地址
     */
    @TableId(value = "address")
    private String address;

    /**
     * 仓库状态（0：关闭；1：开启；9：逻辑删除）
     */
    @TableId(value = "status")
    private Integer status;

    /**
     * 最后修改时间
     */
    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 运费金额
     */
    @TableId(value = "transport_amount")
    private BigDecimal transportAmount;


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
        return "Repository{" +
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
                '}';
    }
}
