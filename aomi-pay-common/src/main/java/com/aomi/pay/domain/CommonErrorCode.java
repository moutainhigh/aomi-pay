
package com.aomi.pay.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常编码
 * 前一位:异常类型标识
 * 第二位第三位:模块标识
 * 后三位:异常标识
 */
@SuppressWarnings("ALL")
public enum CommonErrorCode {
    //---操作成功通用返回码----
    SUCCESS(true, "100000", "操作成功!"),
    //---系统错误通用返回码---
    FAIL(false, "999999", "操作失败"),
    //---业务通用返回码---  desc可自定义
    BUSINESS(false, "300000", "操作失败"),
    //---参数错误通用返回码--- desc可自定义
    PARAM(false, "200000", "参数有误"),

    ////////////////////////////////////参数级别异常详细编码 991xxx//////////////////////////
    E_ANALYSIS("200000", "解析错误"),


    ////////////////////////////////////业务级别异常详细编码 301xxx//////////////////////////  A模块


    ////////////////////////////////////业务级别异常详细编码 302xxx//////////////////////////  B模块


    /**
     * 系统异常
     */
    UNKNOWN("999999", "系统异常");

    @Getter
    @Setter
    String code;

    @Getter
    @Setter
    String desc;

    CommonErrorCode(boolean success, String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private CommonErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
