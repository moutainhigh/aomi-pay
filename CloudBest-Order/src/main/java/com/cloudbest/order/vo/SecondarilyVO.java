package com.cloudbest.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SecondarilyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SecondarilyVO{" +
                "id=" + id +
                ", mainOrderId='" + mainOrderId + '\'' +
                ", ancillaryOrderId='" + ancillaryOrderId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", receiverDetailAddress='" + receiverDetailAddress + '\'' +
                ", deliveryCompany='" + deliveryCompany + '\'' +
                ", deliveryAmount=" + deliveryAmount +
                ", deliveryId='" + deliveryId + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", receiveTime=" + receiveTime +
                ", current=" + current +
                ", size=" + size +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

    /**
     * id
     */
    private Integer id;

    /**
     * 主订单号
     */
    private String mainOrderId;

    /**
     * 子订单号
     */
    private String ancillaryOrderId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货人邮箱
     */
    private String receiverEmail;

    /**
     * 收货人地址
     */
    private String receiverDetailAddress;

    /**
     * 物流公司(配送方式)
     */
    private String deliveryCompany;

    /**
     * 运费
     */
    private BigDecimal deliveryAmount;

    /**
     * 物流单号
     */
    private String deliveryId;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 物流状态
     */
    private String deliveryStatus;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    private Integer deleteStatus;

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

    public String getAncillaryOrderId() {
        return ancillaryOrderId;
    }

    public void setAncillaryOrderId(String ancillaryOrderId) {
        this.ancillaryOrderId = ancillaryOrderId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public BigDecimal getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(BigDecimal deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }
}
