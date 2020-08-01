package com.aomi.pay.enums;

/**
 * 错误码enum
* @author hdq
* @date 2020/7/31 14:32
 */
public enum ErrorCodeEnum {

	//成功
	SUCCESS(200,"操作成功"),
	FAIL(999,"操作失败"),
	SEARCH_SUCCESS(0,"查询成功"),
	
	
	SEARCH_ERROR(9,"查询失败"),

	//系统错误
	ERROR_90001(90001,"系统异常"),

	/**参数错误*/
	ERROR_30001(30001,"参数有误"),


	/**访问无权限*/
	ERROR_40001(40001,"无访问权限"),
	/**帐号登录过期*/
	ERROR_40002(40002,"帐号登录过期"),
	
	//---qp
	ERROR_20000(20000,"系统忙，请稍后重试"),
	ERROR_20001(20001,"手机号码不能为空"),
	ERROR_20002(20002,"图形验证码不能为空"),
	ERROR_20003(20003,"短信验证码不能为空"),
	ERROR_20004(20004,"手机号码不正确"),
	ERROR_20005(20005,"图形验证码不正确"),
	ERROR_20006(20006,"短信验证码不正确"),
	ERROR_20007(20007,"deviceId不能为空"),
	ERROR_20008(20008,"参数不能为空"),
	ERROR_20009(20009,"60s内不能重复获取验证码"),
	ERROR_20010(20010,"用户不存在"),
	ERROR_20011(20011,"该用户已被邀请，不能重复邀请"),
	ERROR_20012(20012,"用户Id错误"),
	ERROR_20013(20013,"公司Id错误"),
	ERROR_20014(20014,"密码不能为空"),
	ERROR_20015(20015,"密码错误"),
	ERROR_20016(20016,"两次密码输入不一致"),
	ERROR_20017(20017,"公司已经存在"),
	ERROR_20018(20018,"公司名称不能为空"),
	ERROR_20019(20019,"企业营业执照号不能为空"),
	ERROR_20020(20020,"税务登记号不能为空"),
	ERROR_20021(20021,"法人代表姓名不能为空"),
	ERROR_20022(20022,"法人代表电话不能为空"),
	ERROR_20023(20023,"业务负责人姓名不能为空"),
	ERROR_20024(20024,"业务负责人电话不能为空"),
	ERROR_20025(20025,"服务不能取消"),
	ERROR_20026(20026,"服务不存在"),
	ERROR_20027(20027,"服务不能删除"),
	ERROR_20028(20028,"未完成服务不能退款"),
	ERROR_20029(20029,"用户公司不存在"),
	ERROR_20030(20030,"商户公司不存在"),
	ERROR_20031(20031,"用户已存在"),
	ERROR_20032(20032,"角色不存在"),
	ERROR_20033(20033,"角色无权限"),
	ERROR_20034(20034,"没有对应的退款单"),
	ERROR_20035(20035,"没有对应的投诉单"),
	ERROR_20036(20036,"原登录密码错误"),
	ERROR_20037(20037,"新密码不能与原密码相同"),
	ERROR_20038(20038,"已分配的申请单不可删除"),

	/** 上传服务 204** */
	ERROR_20401(20401,"文件不存在");
	
	

	;
	
	private int code;
	
	private String msg;
	
	/**
	 * 构造函数
	 * @param code
	 * @param msg
	 */
	ErrorCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
