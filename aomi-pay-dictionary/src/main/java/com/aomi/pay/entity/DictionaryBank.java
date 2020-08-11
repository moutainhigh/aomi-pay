package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 银行编码字典表
 * </p>
 *
 * @author hdq
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_dictionary_bank")
public class DictionaryBank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 银行编号
     */
    @TableField("bank_id")
    private Long bankId;
    /**
     * 银行全称
     */
    @TableField("bank_name")
    private String bankName;

}
