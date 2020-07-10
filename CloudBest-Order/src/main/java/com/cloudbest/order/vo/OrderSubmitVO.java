package com.cloudbest.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


//确认订单需要提交的数据
@Data
public class OrderSubmitVO {

        private Long userId;
        //订单状态
        private Integer status;
        //主订单号
        private String mainOrderId;
        //子订单号
        private String ancillaryOrderId;

        private String userName;

        private String address; // 收货地址

        private Integer payType; // 支付方式

        private BigDecimal deliveryAmount;//运费

        private Integer deliveryCompany;// 配送公司（配送方式）

        private String deliveryId;// 物流单号

        private List<OrderItemVO> orderItemVOS; // 订单详情

        private BigDecimal useIntegration; // 下单时使用的总购物券

        private String note;//订单备注

        //发票相关信息  暂无
        // 营销相关信息  暂无
        private BigDecimal totalPrice; // 所有商品总价，用于验价

        private String orderToken; // 防重，订单编号

        @Override
        public String toString() {
                return "OrderSubmitVO{" +
                        "userId=" + userId +
                        ", status=" + status +
                        ", mainOrderId='" + mainOrderId + '\'' +
                        ", ancillaryOrderId='" + ancillaryOrderId + '\'' +
                        ", userName='" + userName + '\'' +
                        ", address='" + address + '\'' +
                        ", payType=" + payType +
                        ", deliveryAmount=" + deliveryAmount +
                        ", deliveryCompany=" + deliveryCompany +
                        ", deliveryId='" + deliveryId + '\'' +
                        ", orderItemVOS=" + orderItemVOS +
                        ", useIntegration=" + useIntegration +
                        ", note='" + note + '\'' +
                        ", totalPrice=" + totalPrice +
                        ", orderToken='" + orderToken + '\'' +
                        '}';
        }

        public String getNote() {
                return note;
        }

        public void setNote(String note) {
                this.note = note;
        }

        public BigDecimal getDeliveryAmount() {
                return deliveryAmount;
        }

        public void setDeliveryAmount(BigDecimal deliveryAmount) {
                this.deliveryAmount = deliveryAmount;
        }

        public BigDecimal getUseIntegration() {
                return useIntegration;
        }

        public void setUseIntegration(BigDecimal useIntegration) {
                this.useIntegration = useIntegration;
        }

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public Integer getPayType() {
                return payType;
        }

        public void setPayType(Integer payType) {
                this.payType = payType;
        }

        public Integer getDeliveryCompany() {
                return deliveryCompany;
        }

        public void setDeliveryCompany(Integer deliveryCompany) {
                this.deliveryCompany = deliveryCompany;
        }

        public List<OrderItemVO> getOrderItemVOS() {
                return orderItemVOS;
        }

        public void setOrderItemVOS(List<OrderItemVO> orderItemVOS) {
                this.orderItemVOS = orderItemVOS;
        }

        public BigDecimal getTotalPrice() {
                return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
        }

        public String getOrderToken() {
                return orderToken;
        }

        public void setOrderToken(String orderToken) {
                this.orderToken = orderToken;
        }

        public Integer getStatus() {
                return status;
        }

        public void setStatus(Integer status) {
                this.status = status;
        }

        public String getAncillaryOrderId() {
                return ancillaryOrderId;
        }

        public void setAncillaryOrderId(String ancillaryOrderId) {
                this.ancillaryOrderId = ancillaryOrderId;
        }

        public String getMainOrderId() {
                return mainOrderId;
        }

        public void setMainOrderId(String mainOrderId) {
                this.mainOrderId = mainOrderId;
        }

        public String getDeliveryId() {
                return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
                this.deliveryId = deliveryId;
        }
}
