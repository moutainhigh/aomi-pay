package com.aomi.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 银行编码VO
 * </p>
 *
 * @author hdq
 * @since 2020-08-11
 */
@Data
@ApiModel(value = "银行编码接口返回封装")
public class DictionaryBankVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    /**
     * 银行编号
     */
    @ApiModelProperty(value = "银行编号", name = "bankId")
    private Long bankId;

    /**
     * 银行全称
     */
    @ApiModelProperty(value = "银行全称", name = "bankName")
    private String bankName;

}
