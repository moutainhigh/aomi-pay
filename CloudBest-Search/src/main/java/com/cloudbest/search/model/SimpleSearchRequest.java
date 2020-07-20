package com.cloudbest.search.model;

import com.cloudbest.common.annotations.Validator;
import com.cloudbest.common.domain.BasePageRequest;
import com.cloudbest.common.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/7/17 15:02
 * @Description 简单搜索接口接收参数封装
 */
@ApiModel(value="简单搜索接口接收参数封装")
@Data
public class SimpleSearchRequest extends BasePageRequest {

    /**
     * 关键字
     */
    @Validator(maxLength = 255,description = "关键字")
    @ApiModelProperty(value="关键字",name="keywords")
    private String keywords;

}
