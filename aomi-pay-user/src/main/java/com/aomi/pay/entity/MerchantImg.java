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
    private String platformTag;

    /**
     * 图片类型（
     * 01：身份证正面
     * 02：身份证反面
     * 03:  手持身份证
     * 04：银行卡正面照
     * 05：营业执照
     * 06：组织机构代码证
     * 07：税务登记照片
     * 08：开户许可证
     * 09：门头照
     * 10：收银台照片
     * 11：店铺全景照
     * 12：对私结算授权书照片
     * 13：被委托人身份证照正面
     * 14：被委托人身份证照反面
     * 15：被委托人手持身份证照片
     * 16：委托清算协议书照片
     * 17：行业许可证照片
     * 18：补充照片
     *
     */
    private String type;

    /**
     * 上传平台后返回的imgCode
     */
    private String imgCode;

    private LocalDateTime updateTime;


}
