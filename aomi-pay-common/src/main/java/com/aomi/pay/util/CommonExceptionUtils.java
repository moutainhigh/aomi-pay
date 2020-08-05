package com.aomi.pay.util;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.exception.ParamException;
import com.aomi.pay.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * 公共调用  异常util
 *
 * @author hdq
 * @date 2020/7/15 13:53
 */
@SuppressWarnings({"rawtypes"})
@Slf4j

public class CommonExceptionUtils {

    /**
     * 系统通用异常
     * code:999999  desc:系统错误
     */
    public static void throwSystemException() throws SystemException {
        throw new SystemException(CommonErrorCode.FAIL);
    }

    /**
     * 参数级别通用异常
     * code:999100   desc: 自定义
     * @param desc 返回描述
     */
    public static void throwParamException(String desc) throws ParamException {
        throw new ParamException(CommonErrorCode.PARAM.getCode(),desc);
    }

    /**
     * 参数级别通用异常
     * commonErrorCode
     * @param commonErrorCode 公用异常类
     */
    public static void throwParamException(CommonErrorCode commonErrorCode) throws ParamException {
        throw new ParamException(commonErrorCode);
    }

    /**
     * 业务级别通用异常
     * desc 自定义
     * @param desc 返回描述   固定通用业务返回 900000
     */
    public static void throwBusinessException(String desc) throws BusinessException {
        throw new BusinessException(CommonErrorCode.BUSINESS.getCode(), desc);
    }

    /**
     * 业务级别通用异常
     *
     * @param commonErrorCode 公用异常类
     */
    public static void throwBusinessException(CommonErrorCode commonErrorCode) throws BusinessException {
        throw new BusinessException(commonErrorCode);
    }

    /**
     * 业务级别通用异常
     * 自定义code  自定义desc
     * @param desc 返回描述
     */
    public static void throwBusinessException(String code, String desc) throws BusinessException {
        throw new BusinessException(code, desc);
    }

    /**
     * 系统级别通用异常  - code:9999  自定义desc
     *
     * @param desc 返回描述
     */
    public static void throwSystemException(String desc) throws SystemException {
        throw new SystemException(desc);
    }

    /**
     * 系统级别通用异常  - code:9999  自定义desc
     *
     * @param commonErrorCode 返回描述
     */
    public static void throwSystemException(CommonErrorCode commonErrorCode) throws SystemException {
        throw new SystemException(commonErrorCode);
    }

    /**
     * @Desc 对象判空并且抛出业务级别异常返回信息
     */
    public static void isNull(Serializable s, String code, String desc) throws BusinessException {
        if (s == null) {
            throw new BusinessException(code, desc);
        }
    }

    /**
     * @Desc 对象判空并且抛出业务级别异常返回信息
     */
    public static void isNull(Serializable s, CommonErrorCode commonErrorCode) throws BusinessException {
        if (s == null) {
            throw new BusinessException(commonErrorCode);
        }
    }

    /**
     * @param desc 返回描述自定义
     * @Desc 对象判空并且抛出业务级别异常返回信息
     */
    public static void isNull(Serializable s, String desc) throws BusinessException {
        if (s == null) {
            throw new BusinessException(desc);
        }
    }

    /**
     * 对象非空判断并且抛出业务级别异常返回信息
     *
     * @param s errorCode info
     */
    public static void isNotNull(Serializable s, String code, String info) throws BusinessException {
        if (s != null) {
            throw new BusinessException(code, info);
        }
    }

    /**
     * list判空并且抛出业务级别异常返回信息
     *
     * @param list errorCode info
     */
    public static void isNotNull(List list, String code, String info) throws BusinessException {
        if (list != null && !list.isEmpty()) {
            throw new BusinessException(code, info);
        }
    }

    /**
     * list非空判断并且抛出业务级别异常返回信息
     *
     * @param list errorCode info
     */
    public static void isNull(List list, String code, String info) throws Exception {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(code, info);
        }
    }

    /**
     * list非空判断并且抛出业务级别异常返回信息
     *
     * @param list errorCode info
     */
    public static void isNull(List list, CommonErrorCode commonErrorCode) throws Exception {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(commonErrorCode);
        }
    }
}
