package com.aomi.pay.vo;

import lombok.Data;

@Data
public class ProductVO {

    private String instMchtNo;//机构商户号
    private String mchtNO;//平台商户号
    private Integer productId;//产品Id

    private String productCode;//产品类型
    private String modelId;//签约费率id

}
