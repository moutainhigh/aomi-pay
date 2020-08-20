package com.aomi.pay.model;

import com.github.dozermapper.core.Mapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author hdq
 * @Date 2020/8/18
 * @Description 商户信息查询接口返回参数封装
 */
@ApiModel(value = "商户信息查询接口返回参数封装")
@Data
public class GetMerchantInfoResponse {

    /**
     * 商户id（机构）账户
     */
    @ApiModelProperty(value = "商户id", name = "id")
    private Long id;

    /**
     * 平台生成的商户号
     */
    @ApiModelProperty(value = "平台生成的商户号", name = "platformId")
    private String platformId;

    /**
     * 商户简称
     */
    @ApiModelProperty(value = "商户简称", name = "simpleName")
    private String simpleName;

    /**
     * 推广员id
     */
    @ApiModelProperty(value = "商户简称", name = "simpleName")
    private String bdNo;

    /**
     * 产品利率列表
     */
    private List<ProductResponse> productList;


}
