package com.cloudbest.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("yh_customer_bind")
public class YhCustomerBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 云上优选用户ID
     */
    private Long cloudUserId;

    /**
     * 友惠用户ID
     */
    private Long yhUserId;

    /**
     * 绑定时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bindTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCloudUserId() {
        return cloudUserId;
    }

    public void setCloudUserId(Long cloudUserId) {
        this.cloudUserId = cloudUserId;
    }

    public Long getYhUserId() {
        return yhUserId;
    }

    public void setYhUserId(Long yhUserId) {
        this.yhUserId = yhUserId;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public void setBindTime(LocalDateTime bindTime) {
        this.bindTime = bindTime;
    }

}
