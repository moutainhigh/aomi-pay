package com.cloudbest.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MainEntityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "MainEntityVO{" +
                "receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverDetailAddress='" + receiverDetailAddress + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", mainOrderId='" + mainOrderId + '\'' +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", payAmount=" + payAmount +
                ", payType=" + payType +
                ", orderTime=" + orderTime +
                ", overTime=" + overTime +
                ", payTime=" + payTime +
                ", note='" + note + '\'' +
                ", payStatus=" + payStatus +
                ", current=" + current +
                ", size=" + size +
                ", deleteStatus=" + deleteStatus +
                '}';
    }


    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货人地址
     */
    private String receiverDetailAddress;
    /**
     * id
     */
    private Integer id;
    private Integer status;
    private Integer skuId;
    private Integer spuId;
    private BigDecimal price;
    private String img;
    private String name;
    private Integer count;
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
     * 订单剩余关闭时间
     */
    private Long overTime;
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
    /**
     * 删除状态
     */
    private Integer deleteStatus;


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Long getOverTime() {
        return overTime;
    }

    public void setOverTime(Long overTime) {
        this.overTime = overTime;
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

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }


}
