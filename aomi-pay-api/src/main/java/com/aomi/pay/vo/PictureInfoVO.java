package com.aomi.pay.vo;

import lombok.Data;

@Data
public class PictureInfoVO {

    private String picType;//类型
    private String pic;//图片地址加密后字符串
    private String picName;//图片名称
    private String instId;//商户id；

}
