package com.cloudbest.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScoreSourceVO implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 云上优选用户ID
     */
    private Long customerId;

    /**
     * 用户购物券来源
     */
    private String integralSource;

    /**
     * 正标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 变更时间
     */
    private LocalDateTime localDateTime;

    /**
     * 变更类型
     */
    private Integer changeType;

    /**
     * 用户购物券数额
     */
    private BigDecimal num;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIntegralSource() {
        return integralSource;
    }

    public void setIntegralSource(String integralSource) {
        this.integralSource = integralSource;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
}
