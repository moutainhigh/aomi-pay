package com.aomi.pay.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商户语音播报设备绑定关系表
 * </p>
 *
 * @author hdq
 * @since 2020-08-22
 */
@Data
@TableName("t_merchant_audio_bind")
public class MerchantAudioBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 播报设备码
     */
    @TableField("audio_code")
    private String audioCode;
    /**
     * 播报设备类型(当前为0)
     */
    @TableField("audio_type")
    private Integer audioType;
    /**
     * 商户id
     */
    @TableField("merchant_id")
    private Long merchantId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
