package com.aomi.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureVO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String picType;//类型
    private String picName;//图片名称
    private String userId;//商户id；
    private String imagStr;




}
