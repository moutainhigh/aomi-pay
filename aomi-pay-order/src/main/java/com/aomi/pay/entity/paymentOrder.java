package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hdq
 * @Date 2020/8/4 18:10
 * @Version 1.0
 */
@Data
@TableName("t_payment_order")
public class paymentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableId(value = "order_code")
    private String orderCode;

    @TableId(value = "order_desc")
    private String orderDesc;

    @TableId(value = "order_desc_code")
    private String orderDescCode;

    private String amount;

    @TableId(value = "real_amount")
    private String realAmount;

    @TableId(value = "user_id")
    private String userId;

    private String openid;

    private String phone;

    @TableId(value = "phone_bill")
    private String phoneBill;

    @TableId(value = "car_no")
    private String carNo;

    @TableId(value = "brand_id")
    private String brandId;

    @TableId(value = "brand_name")
    private String brandName;

    @TableId(value = "auto_clearing")
    private String autoClearing;

    private String rate;

    @TableId(value = "extra_fee")
    private String extraFee;

    @TableId(value = "cost_fee")
    private String costFee;

    @TableId(value = "third_order_code")
    private String thirdOrderCode;

    @TableId(value = "order_type")
    private String orderType;

    @TableId(value = "third_level_id")
    private String thirdLevelId;

    @TableId(value = "channel_id")
    private String channelId;

    @TableId(value = "channel_tag")
    private String channelTag;

    @TableId(value = "channel_type")
    private String channelType;

    @TableId(value = "out_notify_url")
    private String outNotifyUrl;

    @TableId(value = "out_mer_order_code")
    private String outMerOrderCode;

    @TableId(value = "out_return_url")
    private String outReturnUrl;

    @TableId(value = "channel_name")
    private String channelName;

    @TableId(value = "order_status")
    private String orderStatus;

    private String remark;

    @TableId(value = "update_time")
    private String updateTime;

    @TableId(value = "bank_card")
    private String bankCard;

    @TableId(value = "debit_bank_card")
    private String debitBankCard;

    @TableId(value = "create_date")
    private String createDate;
}
