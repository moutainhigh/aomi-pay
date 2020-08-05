package com.aomi.pay.exception;

import com.aomi.pay.domain.CommonErrorCode;

/**
 * @Author hdq
 * @Date 2020/7/15 15:13
 * @Description  系统异常
 */
public class SystemException extends AbstractGenericException {

    private static final long serialVersionUID = 1L;

    /**
     * <br>------------------------------<br>
     *
     * @param desc 返回描述
     */
    public SystemException(String desc) {
        super(desc);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code 返回码
     * @param desc 返回描述
     */
    public SystemException(String code, String desc) {
        super(code);;
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code      返回码
     * @param desc      返回描述
     * @param throwable
     */
    public SystemException(String code, String desc, Throwable throwable) {
        super(code, desc, throwable);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code      返回码
     * @param throwable
     */
    public SystemException(String code, Throwable throwable) {
        super(code, throwable);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code      返回码
     * @param arguments
     */
    public SystemException(String code, Object... arguments) {
        super(code, arguments);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param commonErrorCode 公共异常编码
     */
    public SystemException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode.getCode(), commonErrorCode.getDesc());
    }
}
