package com.cloudbest.search.util;

import com.cloudbest.common.domain.BaseResponse;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.exception.BusinessException;
import com.cloudbest.common.exception.ParamException;
import com.cloudbest.common.exception.SystemException;
import com.cloudbest.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @Project tax
 * @Package com.ebuy.tax.common.exceptionhandler
 * @Author hdq
 * @Date 2020/7/15 15:34
 * @Description 异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param e
     * @param response
     * @Description 参数异常
     * @author hdq
     */
    @ExceptionHandler(ParamException.class)
    public BaseResponse exceptionHandler(ParamException e, HttpServletResponse response) {
        log.error("参数级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.PARAM.getCode() : e.getCode(), e.getDesc());
    }

    /**
     * @param e
     * @param response
     * @return com.ebuy.tax.common.vo.BaseResponse
     * @Description 业务异常
     * @author hdq
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse exceptionHandler(BusinessException e, HttpServletResponse response) {
        log.error("业务级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.BUSINESS.getCode() : e.getCode(), e.getDesc());
    }

    /**
     * @param e
     * @param response
     * @return com.ebuy.tax.common.vo.BaseResponse
     * @Description 系统异常
     * @author hdq
     */
    @ExceptionHandler(SystemException.class)
    public BaseResponse exceptionHandler(SystemException e, HttpServletResponse response) {
        log.error("系统级别异常处理:", e);
        return new BaseResponse(e.getCode() == null? CommonErrorCode.FAIL.getCode() : e.getCode(), StringUtil.isBlank(e.getDesc()) ? CommonErrorCode.FAIL.getDesc() : e.getDesc());
    }

    /**
     * @param e
     * @param response
     * @return com.ebuy.tax.common.vo.BaseResponse
     * @Description 全局异常处理
     * @author hdq
     */
    @ExceptionHandler
    public BaseResponse exceptionHandler(Exception e, HttpServletResponse response) {
        log.error("全局异常处理:", e);
        return new BaseResponse(CommonErrorCode.FAIL);
    }
}
