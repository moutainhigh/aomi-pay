package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.domain.BasePageRequest;
import com.aomi.pay.enums.RegexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author hdq
 * @date : 2020/7/22 9:28
 * @Description 综合搜索接口接收参数封装
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "综合搜索接口接收参数封装")
@Data
public class SearchRequest extends BasePageRequest {

    /**
     * 关键字
     */
    @Validator(isNotNull = true, maxLength = 255, description = "关键字")
    @ApiModelProperty(value = "关键字", name = "keywords", required = true)
    private String keywords;

    /**
     * 分类id
     */
    @Validator(maxLength = 8, description = "分类id", regexType = RegexEnum.NUMBER)
    @ApiModelProperty(value = "分类id", name = "categoryId")
    private String categoryId;

    /**
     * 排序   排序字段:1->按销量；2->价格从低到高；3->价格从高到低
     */
    @Validator(maxLength = 1, description = "排序", regexType = RegexEnum.NUMBER)
    @ApiModelProperty(value = "排序  排序字段:1->按销量 从高到底；2->价格从低到高；3->价格从高到低", name = "sort")
    private String sort;
}
