package com.cloudbest.order.otherentity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CustomerAddr implements Serializable {

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 自增主键ID
     */

    private Integer id;

    /**
     * 用户ID
     */
    private Long customerId;

    /**
     * 省份ID
     */
    private String province;

    /**
     * 市ID
     */
    private String city;

    /**
     * 区ID
     */
    private String district;

    /**
     * 具体的地址门牌号
     */
    private String address;

    /**
     * 是否默认
     */
    private Integer isDefault;

    /**
     * 最后修改时间
     */
    private LocalDateTime modifiedTime;

    @Override
    public String toString() {
        return "CustomerAddr{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", province=" + province +
                ", city=" + city +
                ", district=" + district +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
