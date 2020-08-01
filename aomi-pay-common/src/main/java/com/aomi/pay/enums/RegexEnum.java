package com.aomi.pay.enums;

/**
 * @author hdq
 * @date 2020/7/15 13:33
 * @desc
 */
@SuppressWarnings({"ALL", "AlibabaEnumConstantsMustHaveComment"})
public enum RegexEnum {
    /**空*/
    NONE,
    /**特殊字符*/
    SPECIALCHAR,
    /**中文*/
    CHINESE,
    /**邮箱地址*/
    EMAIL,
    /**数字*/
    NUMBER,
    /**手机号码*/
    PHONENUMBER,
    /**页码*/
    PAGENO,
    /**页面大小*/
    PAGESIZE,
    /**时间 YYYY-MM-DD HH:mm:ss*/
    TIME
}

