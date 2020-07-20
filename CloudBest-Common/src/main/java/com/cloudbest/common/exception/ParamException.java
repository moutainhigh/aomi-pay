/**
 * 请求参数异常类
 */
package com.cloudbest.common.exception;

import com.cloudbest.common.domain.CommonErrorCode;

/**
 * 参数异常
 * @author hdq
 * @Date 2020/7/15 14:36
 */
@SuppressWarnings("serial")
public class ParamException extends AbstractGenericException {

    /**
     * <br>------------------------------<br>
     * @param desc 返回描述
     */
    public ParamException(String desc) {
        super(desc);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code 返回码
     * @param desc 返回描述
     */
    public ParamException(Integer code, String desc) {
        super(code, desc);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code 返回码
     * @param desc 返回描述
     * @param throwable
     */
    public ParamException(Integer code, String desc, Throwable throwable) {
        super(code, desc, throwable);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code 返回码
     * @param throwable
     */
    public ParamException(Integer code, Throwable throwable) {
        super(code, throwable);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param code 返回码
     * @param arguments
     */
    public ParamException(Integer code, Object... arguments) {
        super(code, arguments);
    }

    /**
     * <br>------------------------------<br>
     *
     * @param commonErrorCode 公共异常编码
     */
    public ParamException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode.getCode(), commonErrorCode.getDesc());
    }
}
