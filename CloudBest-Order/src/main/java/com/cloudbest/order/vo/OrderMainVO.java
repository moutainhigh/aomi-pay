package com.cloudbest.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderMainVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "OrderMainVO{" +
                "id=" + id +
                ", status=" + status +
                ", mainOrderId='" + mainOrderId + '\'' +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", payAmount=" + payAmount +
                ", payType=" + payType +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                ", note='" + note + '\'' +
                ", payStatus=" + payStatus +
                ", current=" + current +
                ", size=" + size +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

    /**
     * id
     */
    private Integer id;

    private Integer status;

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
     * 支付方式【1->支付宝；2->微信；3->银联；】
     */
    private Integer payType;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 订单状态【1->待付款；2->待发货；3->已发货；4->已完成；5->已取消】
     */
    private Integer payStatus;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    private Integer deleteStatus;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

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
}
