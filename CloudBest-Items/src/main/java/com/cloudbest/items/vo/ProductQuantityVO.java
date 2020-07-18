package com.cloudbest.items.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductQuantityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * skuid
     */
    private Integer skuId;
    /**
     * itemid
     */
    private String itemId;
    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

}
