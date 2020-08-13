package com.aomi.pay.util;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.exception.ParamException;
import com.aomi.pay.exception.SystemException;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @Project tax
 * @Package com.ebuy.tax.pay.exceptionhandler
 * @Author hdq
 * @Date 2020/7/15 15:34
 * @Description 异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @Description 参数异常
     * @author hdq
     */
    @ExceptionHandler(ParamException.class)
    public BaseResponse exceptionHandler(ParamException e, HttpServletResponse response) {
        log.error("参数级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.PARAM.getCode() : e.getCode(), e.getDesc());
    }

    /**
     * @return com.ebuy.tax.pay.vo.BaseResponse
     * @Description 业务异常
     * @author hdq
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse exceptionHandler(BusinessException e, HttpServletResponse response) {
        log.error("业务级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.BUSINESS.getCode() : e.getCode(), e.getDesc());
    }

    /**
     * @return com.ebuy.tax.pay.vo.BaseResponse
     * @Description 系统异常
     * @author hdq
     */
    @ExceptionHandler(SystemException.class)
    public BaseResponse exceptionHandler(SystemException e, HttpServletResponse response) {
        log.error("系统级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.FAIL.getCode() : e.getCode(), StringUtil.isBlank(e.getDesc()) ? CommonErrorCode.FAIL.getDesc() : e.getDesc());
    }

    /**
     * @return com.ebuy.tax.pay.vo.BaseResponse
     * @Description 全局异常处理
     * @author hdq
     */
    @ExceptionHandler
    public BaseResponse exceptionHandler(Exception e, HttpServletResponse response) {
        log.error("全局异常处理:", e);
        return new BaseResponse(CommonErrorCode.FAIL);
    }
}
