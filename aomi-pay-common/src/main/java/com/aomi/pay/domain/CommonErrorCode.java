
package com.aomi.pay.domain;

/**
 * 异常编码
 * 前一位:异常类型标识
 * 第二位第三位:模块标识
 * 后三位:异常标识
 */
@SuppressWarnings("ALL")
public enum CommonErrorCode implements ErrorCode {
	//---操作成功通用返回码----
	SUCCESS(true,"100000","操作成功!"),
	//---系统错误通用返回码---
	FAIL(false,"999999","操作失败"),
	//---业务通用返回码---  desc可自定义
	BUSINESS(false,"300000","操作失败"),
	//---参数错误通用返回码--- desc可自定义
	PARAM(false,"200000","参数有误"),

	////////////////////////////////////参数级别异常详细编码 991xxx//////////////////////////
	E_ANALYSIS("200000","解析错误"),


	////////////////////////////////////业务级别异常详细编码 301xxx//////////////////////////  A模块
	E_301001("301001","上传图片失败"),

	E_301002("301002","获取信息失败，网络异常"),
	E_301003("301003","商户id为空"),
	E_301004("301004","开通产品失败"),

//	E_301003(300003,"appId为空"),
//	E_301005(300005,"服务类型编码为空"),
//	E_301006(300006,"订单金额转换异常"),
//	E_301007(300007,"原始支付渠道为空"),
//	E_301008(300008,"已存在相同的支付参数，不可重复配置"),
//	E_301009(300009,"传入对象为空或者缺少必要的参数"),
//	E_301010(300010,"应用没有绑定服务类型，不允许配置参数"),


	////////////////////////////////////业务级别异常详细编码 302xxx//////////////////////////  B模块


	/**
	 * 系统异常
	 */
	UNKNOWN("999999","系统异常");


	String code;
	String desc;
	boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success){this.success = success;}

	CommonErrorCode(boolean success, String code, String desc) {
		this.success=success;
		this.code = code;
		this.desc = desc;
	}
	private CommonErrorCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
