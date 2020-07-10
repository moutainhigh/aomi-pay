package com.cloudbest.items.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

public class SupplierVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 供应商编码
     */
    private String supplierShortName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 类型（0：自营；1：平台）
     */
    private Integer type;

    /**
     * 供应商联系人
     */
    private String supplierAdmin;

    /**
     * 供应商联系方式
     */
    private String supplierPhone;

    /**
     * 银行账号
     */
    private String bankCardNo;

    /**
     * 银行开户名称
     */
    private String bankRedisterName;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态（0：关闭；1：开启）
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
        return "SupplierVO{" +
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
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}
