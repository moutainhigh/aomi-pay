package com.aomi.pay.domain;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hdq
 * @Desc 分页请求封装
 * @Date 2020/7/15 16:46
 */
@Data
public class BasePageRequest implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    @Validator(maxLength = 10, regexExpression = RegexUtils.REGEX_PAGENO, description = "页码")
    @ApiModelProperty(value = "页码", example = "1")
    private String pageNo;

    /**
     * 页面大小
     */
    @Validator(maxLength = 10, regexExpression = RegexUtils.REGEX_PAGESIZE, description = "页面大小")
    @ApiModelProperty(value = "页面大小", example = "10")
    private String pageSize;

}
