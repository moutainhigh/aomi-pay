package com.aomi.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_merchant_product")
public class MerchantProduct {
    private Long merchantId;
    private String productCode;
    private String modelId;
    private String platformTag;
    private String describe;

}
