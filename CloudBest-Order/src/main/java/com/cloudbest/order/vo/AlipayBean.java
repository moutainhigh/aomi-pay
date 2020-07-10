package com.cloudbest.order.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * 支付实体对象
 * 根据支付宝接口协议
 *
 */

public class AlipayBean implements Serializable {
	
	/**
	 * 商户订单号，必填
	 * 
	 */
	private String outTradeNo;
	/**
	 * 订单名称，必填
	 */
	private String subject;
	/**
	 * 付款金额(元)，必填
	 */
	private String totalAmount;

	/**
	 * 产品编号，必填
	 */
	private String productCode;
	/**
	 * 商品描述，可空
	 */
	private String body;
	/**
	 * 超时时间参数
	 */
	private String expireTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String timestamp;
	/**
	 * 门店id
	 */
	private Long storeId;

	/**
	 * 订单附件数据， 格式为： SJPAY,平台商户号,Appid,门店,平台渠道编码
	 */
	private String attach;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Override
	public String toString() {
		return "AlipayBean{" +
				"outTradeNo='" + outTradeNo + '\'' +
				", subject='" + subject + '\'' +
				", totalAmount='" + totalAmount + '\'' +
				", productCode='" + productCode + '\'' +
				", body='" + body + '\'' +
				", expireTime='" + expireTime + '\'' +
				", timestamp='" + timestamp + '\'' +
				", storeId=" + storeId +
				", attach='" + attach + '\'' +
				'}';
	}
}
