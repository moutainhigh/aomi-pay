package com.aomi.pay.model;

import com.aomi.pay.annotations.Validator;
import com.aomi.pay.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/7
 * @Description 根据固码获取商户简称接口接收参数封装
 */
@ApiModel(value = "根据固码获取商户简称接口接收参数封装")
@Data
public class GetSimpleNameRequest {

    /**
     * 固码编码
     */
    @Validator(isNotNull = true, maxLength = 22, regexExpression = RegexUtils.REGEX_NUMBER, description = "固码编码")
    @ApiModelProperty(value = "固码编码", name = "fixedQrCode", required = true)
    private String fixedQrCode;

}
