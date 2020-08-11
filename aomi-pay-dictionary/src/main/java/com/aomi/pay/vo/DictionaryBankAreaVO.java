package com.aomi.pay.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 银行区域编码字典表
 * </p>
 *
 * @author hdq
 * @since 2020-08-11
 */
@Data
@ApiModel(value = "银行区域编码接口返回封装")
public class DictionaryBankAreaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id", name = "areaId")
    private Integer areaId;
    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称", name = "name")
    private String name;
    /**
     * 层级1、2、3
     */
    @ApiModelProperty(value = "层级1、2、3", name = "level")
    private Integer level;
    /**
     * 是否直辖市 0否 1是
     */
    @ApiModelProperty(value = "是否直辖市 0否 1是", name = "isCc")
    private Integer isCc;
    /**
     * 是否省直辖市 0否 1是
     */
    @ApiModelProperty(value = "是否省直辖市 0否 1是", name = "isPcc")
    private Integer isPcc;

}
