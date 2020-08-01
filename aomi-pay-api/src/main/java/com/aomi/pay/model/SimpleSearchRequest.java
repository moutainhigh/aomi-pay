package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.domain.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author hdq
 * @Date 2020/7/17 15:02
 * @Description 简单搜索接口接收参数封装
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "简单搜索接口接收参数封装")
@Data
public class SimpleSearchRequest extends BasePageRequest {

    /**
     * 关键字
     */
    @Validator(maxLength = 255, description = "关键字", isNotNull = true)
    @ApiModelProperty(value = "关键字", name = "keywords", required = true)
    private String keywords;

}
