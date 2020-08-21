package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户固码绑定关系表
 * </p>
 *
 * @author author
 * @since 2020-08-17
 */
@Data
@TableName("t_merchant_qr_bind")
public class MerchantQrBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 固定二维码
     */
    private String fixedQrCode;

    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
