package com.aomi.pay.vo;

import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/1 17:23
 * @Version 1.0
 * @Desc  商户基本信息
 */
@Data
public class MchtBase {


    /**
     * 机构方商户号
     */
    private String instMchtNo;
    private String mchtName;
    private String simpleName;
    private String mchtKind;
    private String areaNo;
    private String address;
    private String storePhone;
    private String mchtScope;
    private String mchtType;
    private String nuionpayMacht;

}
