package com.aomi.pay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/1 17:24
 * @Version 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class MchtUser {

    private String phone;
    private String name;
    private String cardNo;
    private String email;
    private String cardDate;
}