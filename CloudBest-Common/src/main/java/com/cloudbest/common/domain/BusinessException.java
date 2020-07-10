package com.cloudbest.common.domain;

/**
 * 自定义的异常类型
 * @author Administrator
 * @version 1.0
 **/
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private Integer code;
    private String desc;
    public BusinessException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
    public BusinessException() {
        super();
    }
    public BusinessException(int code, String s) {
        super();
        this.code = code;
        this.desc = s;
    }
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
