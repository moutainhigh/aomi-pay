package com.aomi.pay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/1 17:27
 * @Version 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class MchtComp {

    private String licenseType;
    private String licenseNo;
    private String licenseDate;

}
