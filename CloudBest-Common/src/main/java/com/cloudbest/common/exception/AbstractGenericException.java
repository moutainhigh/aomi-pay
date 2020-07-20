package com.cloudbest.common.exception;

import jodd.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @Author hdq
 * @Date 2020/7/15 14:36
 * @Description 异常类
 */
@Data
public class AbstractGenericException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1L;

    protected Integer code;

    protected String desc;

    private Object[] arguments;

    private String fullMessage;

    private static String pattern = "code:{0},desc:{1}";

    public AbstractGenericException(String desc) {
        this.desc = desc;
    }

    public AbstractGenericException(Integer code, String desc) {
        super(MessageFormat.format(pattern, code, desc));
        this.code = code;
        this.desc = desc;
        this.fullMessage = MessageFormat.format(pattern, this.code, desc);
    }

    public AbstractGenericException(Integer code, Throwable throwable) {
        super(String.valueOf(code), throwable);
        this.code = code;
        this.fullMessage = StringUtil.toString(throwable);
        this.desc = MessageFormat.format(pattern, this.code, desc);
    }

    public AbstractGenericException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.fullMessage = MessageFormat.format(pattern, this.code, message) + StringUtil.toString(throwable);
        this.code = code;
        this.desc = message;
    }

    public AbstractGenericException(Integer code, Object... arguments) {
        this.code = code;
        this.arguments = arguments;
    }

}
