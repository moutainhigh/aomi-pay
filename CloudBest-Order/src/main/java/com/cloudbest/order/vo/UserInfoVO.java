package com.cloudbest.order.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private String usernam;
    private Integer phone;
    private String address;

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "usernam='" + usernam + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUsernam() {
        return usernam;
    }

    public void setUsernam(String usernam) {
        this.usernam = usernam;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
