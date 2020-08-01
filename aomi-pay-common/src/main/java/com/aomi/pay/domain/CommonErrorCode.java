
package com.aomi.pay.domain;

import lombok.Data;

/**
 * 异常编码 0成功、-1熔断、 -2 标准参数校验不通过 -3会话超时
 * 前两位:服务标识
 * 中间两位:模块标识
 * 后两位:异常标识
 */
@SuppressWarnings("ALL")
public enum CommonErrorCode implements ErrorCode {
	//---操作成功通用返回码----
	SUCCESS(true,100000,"操作成功!"),
	//---系统错误通用返回码---
	FAIL(false,999999,"操作失败"),
	//---业务通用返回码---  desc可自定义
	BUSINESS(false,900000,"操作失败"),
	//---参数错误通用返回码--- desc可自定义
	PARAM(false,999100,"参数有误"),

	E_NO_AUTHORITY(999997,"没有访问权限"),

	CUSTOM(999998,"自定义异常"),

	////////////////////////////////////参数级别异常详细编码 991xxx//////////////////////////
	E_ANALYSIS(999001,"解析错误"),

	/**
	 * 未知错误
	 */
	UNKNOWN(999999,"未知错误");


	int code;
	String desc;
	boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success){this.success = success;}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	CommonErrorCode(boolean success, int code, String desc) {
		this.success=success;
		this.code = code;
		this.desc = desc;
	}
	private CommonErrorCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}


	public static CommonErrorCode setErrorCode(int code) {
		for (CommonErrorCode errorCode : CommonErrorCode.values()) {
			if (errorCode.getCode()==code) {
				return errorCode;
			}
		}
		return null;
	}
}
