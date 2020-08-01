package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/7/15 13:40
 * @Description 根据id删除商品接口接收参数封装
 */
@ApiModel(value="根据id删除商品接口接收参数封装")
@Data
public class DeleteEsItemsByIdRequest {

    /**
     * 商品id
     */
    @Validator(isNotNull = true,maxLength = 8,regexExpression = RegexUtils.REGEX_NUMBER,description = "商品id")
    @ApiModelProperty(value="商品id",name="id",required = true)
    private String id;

}