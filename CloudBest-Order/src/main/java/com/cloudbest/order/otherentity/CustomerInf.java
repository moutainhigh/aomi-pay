package com.cloudbest.order.otherentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("customer_inf")
public class CustomerInf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "customer_id", type = IdType.ID_WORKER)
    private Long customerId;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 用户头像
     */
    private String headportrait;

    /**
     * 昵称
     */
    private String screenname;

    /**
     * 用户真实姓名
     */
    private String customerName;

    /**
     * 证件号码
     */
    private String identityCardNo;

    /**
     * 性别
     */
    private String gender;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 会员生日
     */
    private LocalDateTime birthday;

    /**
     * 最后修改时间
     */
    private LocalDateTime modifiedTime;

    @TableField(exist = false)
    private String token;
}
