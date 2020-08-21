package com.aomi.pay.enums;

/**
 * @Author hdq
 * @Date 2020/8/18
 * @Version 1.0
 */
public enum DBTypeEnum {

    order("order"), user("user");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
