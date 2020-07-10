package com.cloudbest.items.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("c_supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 供应商编码
     */
    @TableId(value = "supplier_short_name")
    private String supplierShortName;

    /**
     * 供应商名称
     */
    @TableId(value = "supplier_name")
    private String supplierName;

    /**
     * 类型（0：自营；1：平台）
     */
    @TableId(value = "type")
    private Integer type;

    /**
     * 供应商联系人
     */
    @TableId(value = "supplier_admin")
    private String supplierAdmin;

    /**
     * 供应商联系方式
     */
    @TableId(value = "supplier_phone")
    private String supplierPhone;

    /**
     * 银行账号
     */
    @TableId(value = "bank_card_no")
    private String bankCardNo;

    /**
     * 银行开户名称
     */
    @TableId(value = "bank_redister_name")
    private String bankRedisterName;

    /**
     * 地址
     */
    @TableId(value = "address")
    private String address;

    /**
     * 状态（0：关闭；1：开启；9：逻辑删除）
     */
    @TableId(value = "status")
    private Integer status;

    /**
     * 最后修改时间
     */
    @TableId(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSupplierAdmin() {
        return supplierAdmin;
    }

    public void setSupplierAdmin(String supplierAdmin) {
        this.supplierAdmin = supplierAdmin;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankRedisterName() {
        return bankRedisterName;
    }

    public void setBankRedisterName(String bankRedisterName) {
        this.bankRedisterName = bankRedisterName;
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

    public String getSupplierShortName() {
        return supplierShortName;
    }

    public void setSupplierShortName(String supplierShortName) {
        this.supplierShortName = supplierShortName;
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
        return "Supplier{" +
                "id=" + id +
                ", supplierShortName='" + supplierShortName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", type=" + type +
                ", supplierAdmin='" + supplierAdmin + '\'' +
                ", supplierPhone='" + supplierPhone + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", bankRedisterName='" + bankRedisterName + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                '}';
    }
}
