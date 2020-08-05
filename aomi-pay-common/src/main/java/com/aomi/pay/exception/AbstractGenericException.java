package com.aomi.pay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @Author hdq
 * @Date 2020/7/15 14:36
 * @Description 异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AbstractGenericException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1L;

    protected String code;

    protected String desc;

    private Object[] arguments;

    private String fullMessage;

    private static String pattern = "code:{0},desc:{1}";

    public AbstractGenericException(String desc) {
        this.desc = desc;
    }

    public AbstractGenericException(String code, String desc) {
        super(MessageFormat.format(pattern, code, desc));
        this.code = code;
        this.desc = desc;
        this.fullMessage = MessageFormat.format(pattern, this.code, desc);
    }

    public AbstractGenericException(String code, Throwable throwable) {
        super(String.valueOf(code), throwable);
        this.code = code;
        this.fullMessage = throwable.toString();
        this.desc = MessageFormat.format(pattern, this.code, desc);
    }

    public AbstractGenericException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.fullMessage = MessageFormat.format(pattern, this.code, message) + throwable.toString();
        this.code = code;
        this.desc = message;
    }

    public AbstractGenericException(String code, Object... arguments) {
        this.code = code;
        this.arguments = arguments;
    }

}
