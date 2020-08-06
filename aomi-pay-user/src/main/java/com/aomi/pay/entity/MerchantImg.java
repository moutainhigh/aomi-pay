package com.aomi.pay.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户图片表
 * </p>
 *
 * @author author
 * @since 2020-08-06
 */
@Data
@TableName("t_merchant_img")
public class MerchantImg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 图片链接地址
     */
    private String imgUrl;

    /**
     * 图片名称
     */
    private String imgName;

    /**
     * 平台标识(huanxun)
     */
    private String platform;

    /**
     * 图片类型（01：身份证正面 02：身份证反面 03：营业执照 04：组织机构代码证 05：
     * 开户许可证 06：门头照 07：其他  08：收银台照片 09：门店内景照片 10：各大餐饮平台入驻照片 11：银行卡图片
     * 12：对私结算授权书照片 13：被委托人手持身份证照片 14：委托清算协议书照片）
     */
    private String type;

    /**
     * 上传平台后返回的imgCode
     */
    private String imgCode;

    private LocalDateTime updateTime;


}
