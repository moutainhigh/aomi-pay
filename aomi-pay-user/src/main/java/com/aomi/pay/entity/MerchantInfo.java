package com.aomi.pay.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2020-08-06
 */
@Data
@TableName("t_merchant_info")
public class MerchantInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户id（机构）
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户简称
     */
    private String simpleName;

    /**
     * 商户种类   B1-个人 
B2-个体工商户或企业
     */
    private String merchantKind;

    /**
     * 经营区域码
     */
    private String areaNo;

    /**
     * 商户详细地址
     */
    private String address;

    /**
     * 店铺联系电话
     */
    private String merchantPhone;

    /**
     * 经营范围
     */
    private String merchantScope;

    /**
     * 是否是特约用户    0-否
1-是

     */
    private String merchantType;

    /**
     * 是否一户一码    0-否
1-是

     */
    private String unionpayMerchant;

    /**
     * 用户/法人信息
     */
    private String legalPersonEmail;

    /**
     * 用户/法人手机号
     */
    private String legalPersonPhone;

    /**
     * 用户名称
     */
    private String legalPersonName;

    /**
     * 证件号码
     */
    private String legalPersonCardno;

    /**
     * 证件号码有效期
     */
    private String legalPersonCardnoDate;

    /**
     * 是否为代理清算账户   0-否
1-是

     */
    private String acctProxy;

    /**
     * 被委托人身份证号
     */
    private String agentCardNo;

    /**
     * 被委托人身份证号码有效期
     */
    private String agentCardDate;

    /**
     * 账户类型     0-对公、1-对私

     */
    private String acctType;

    /**
     * 银行卡号
     */
    private String acctNo;

    /**
     * 账户名称
     */
    private String acctName;

    /**
     * 账号开户行号
     */
    private String acctBankNo;

    /**
     * 账户开行支行地区号
     */
    private String acctZbankCode;

    /**
     * 账户开行支行号
     */
    private String acctZbankNo;

    /**
     * 营业执照号码
     */
    private String licenseNo;

    /**
     * 营业执照有效期
     */
    private String licenseDate;

    /**
     * 营业执照类型     商户种类为B2时为必填
0-企业法人营业执照
1-个体工商户营业执照
     */
    private String licenseType;

    /**
     * 产品编码值
     */
    private String productCode;

    /**
     * 签约费率id值
     */
    private String modelId;

    /**
     * 通道类型      10-环刷商户
11-讯POS个人
12-环POS个人  
20-企业商户
21-讯POS企业
22-环POS企业
1A- 机构合作商户个人
2A-机构合作商户企业
     */
    private String channelKind;

    /**
     * 终端号
     */
    private String sn;

    /**
     * sn终端费率id
     */
    private String snModelId;

    /**
     * 服务类型  01–机构入网,无sn,无snModelId
02-机构入网, 有sn,无snModelId
03-机构入网, 有sn,有snModelId
     */
    private String serviceType;

    /**
     * 0－正常 1－商户新增保存 2－提交待审核 3－商户停用 4－商户注销 5－拒绝待修改',
     * */
    private String status;
    private Long platformId;
    private String unionPayMchtNo;
    private Integer salesmanId;
    private Long password;


}
