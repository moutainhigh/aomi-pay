package com.aomi.pay.vo;

import lombok.Data;

/**
 * @Author hdq
 * @Date 2020/8/7
 * @Version 1.0
 */
@Data
public class Product {

    private String instId;
    private String mchtNo;//平台商户号
    private String productCode;//产品类型
    private String modelId;//签约费率id
}
