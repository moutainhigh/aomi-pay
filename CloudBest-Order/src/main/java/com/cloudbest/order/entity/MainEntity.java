package com.cloudbest.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_main")
public class MainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Override
    public String toString() {
        return "MainEntity{" +
                "id=" + id +
                ", mainOrderId='" + mainOrderId + '\'' +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", payAmount=" + payAmount +
                ", costScore=" + costScore +
                ", payType=" + payType +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                ", note='" + note + '\'' +
                ", payStatus=" + payStatus +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主订单号
     */
    private String mainOrderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单总额
     */
    private BigDecimal totalAmount;

    /**
     * 应付总额
     */
    private BigDecimal payAmount;

    /**
     * 花费购物券
     */
    private BigDecimal costScore;

    /**
     * 支付方式【1->支付宝；2->微信；3->银联；4->全购物券】
     */
    private Integer payType;

    /**
     * 下单时间
     */

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    /**
     * 支付时间
     */

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 订单状态【1->待付款；2->待发货；3->待收货；4->待评价；5->已取消】
     */
    private Integer payStatus;

    /**
     * 删除状态【1-删除；0-未删除】
     */

    private Integer deleteStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getCostScore() {
        return costScore;
    }

    public void setCostScore(BigDecimal costScore) {
        this.costScore = costScore;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
