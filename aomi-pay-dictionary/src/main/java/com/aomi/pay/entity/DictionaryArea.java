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
 * 区域编码字典表
 * </p>
 *
 * @author hdq
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_dictionary_area")
public class DictionaryArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 区域id
     */
    @TableField("area_id")
    private Integer areaId;
    /**
     * 区域名称
     */
    private String name;
    /**
     * 层级1、2、3
     */
    private Integer level;
    /**
     * 是否直辖市 0否 1是
     */
    @TableField("is_cc")
    private Integer isCc;
    /**
     * 是否省直辖市 0否 1是
     */
    @TableField("is_pcc")
    private Integer isPcc;

}
