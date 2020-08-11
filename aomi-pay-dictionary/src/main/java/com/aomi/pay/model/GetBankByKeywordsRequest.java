package com.aomi.pay.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/10
 * @Description 根据关键字模糊匹配银行信息接口接收参数封装
 */
@ApiModel(value = "根据关键字模糊匹配银行信息接口接收参数封装")
@Data
public class GetBankByKeywordsRequest {

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字", name = "keywords")
    private String keywords;

}
