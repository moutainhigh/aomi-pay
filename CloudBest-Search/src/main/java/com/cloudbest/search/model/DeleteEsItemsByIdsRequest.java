package com.cloudbest.search.model;

import com.cloudbest.common.annotations.Validator;
import com.cloudbest.common.util.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author hdq
 * @Date 2020/7/15 18:34
 * @Description 根据id批量删除商品接口接收参数封装
 */
@ApiModel(value="根据id批量删除商品接口接收参数封装")
@Data
public class DeleteEsItemsByIdsRequest {

    /**
     * 商品id
     */
    @Validator(isNotNull = true,description = "商品ids")
    @ApiModelProperty(value="商品ids",name="ids",required = true)
    private List<Integer> ids;

}
