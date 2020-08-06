package com.aomi.pay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/1 17:23
 * @Version 1.0
 * @Desc  商户基本信息
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class MchtBase {


    private String mchtScope;
    private String mchtKind;
    /**
     * 机构方商户号
     */
    private String instMchtNo;
    private String address;
    private String mchtName;
    private String simpleName;
    private String areaNo;
    private String mchtType;
    private String storePhone;

}
