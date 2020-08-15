
package com.aomi.pay.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常编码
 * 前一位:异常类型标识
 * 第二位第三位:模块标识
 * 后三位:异常标识
 */
@SuppressWarnings("ALL")
public enum CommonErrorCode {
    //---操作成功通用返回码----
    SUCCESS(true, "100000", "操作成功!"),
    //---系统错误通用返回码---
    FAIL(false, "999999", "操作失败"),
    //---业务通用返回码---  desc可自定义
    BUSINESS(false, "300000", "操作失败"),
    //---参数错误通用返回码--- desc可自定义
    PARAM(false, "200000", "参数有误"),

	////////////////////////////////////参数级别异常详细编码 991xxx//////////////////////////
	E_ANALYSIS("200000","解析错误"),


	////////////////////////////////////业务级别异常详细编码 301xxx//////////////////////////  A模块
	E_301001("301001","上传图片失败"),
	E_301002("301002","获取信息失败，网络异常"),
	E_301003("301003","商户id为空"),
	E_301004("301004","开通产品失败"),
	E_301005("301005","图片大小不超2M"),
	E_301006("301006","图片上传数量不足"),
	E_301007("301007","商户号为空"),
	E_301008("301008","商户手机号为空"),
	E_301009("301009","填写信息为为空"),
	E_301010("301010","请检查填写信息是否正确"),
	E_301011("301011","产品id为空"),


	////////////////////////////////////用户异常编码 //////////////////////////
	E_900101("900101","地址ID为空"),
	E_900102("900102","验证码错误"),
	E_900103("900103","验证码为空"),
	E_900104("900104","查询结果为空"),
	E_900105("900105","ID格式不正确或超出Long存储范围"),
	E_900106("900106","上传错误"),
	E_900107("900107","发送验证码错误"),
	E_900108("900108","该手机号未注册"),
	E_900109("900109","今日输错密码超过10次，账号暂时被冻结，请明日再进行尝试"),
	E_900110("900110","用户名为空"),
	E_900111("900111","密码为空"),
	E_900112("900112","手机号为空"),
	E_900113("900113","手机号已存在"),
	E_900114("900114","用户名已存在"),
	E_900115("900115","密码不正确"),
	E_900116("900116","地址为空"),
	E_900117("900117","用户ID为空"),
	E_900118("900118","未找到该用户"),
	E_900119("900119","新密码为空"),
	E_900120("900120","用户收藏为空"),
	E_900121("900121","token验证失败"),
	E_900122("900122","手机号码或密码为空"),
	E_900123("900123","手机号码格式不正确"),
	E_900124("900124","身份证验证失败"),
	E_900125("900125","姓名为空"),
	E_900126("900126","用户对象为空"),
	E_900127("900127","手机号码与密码不匹配"),
	E_900128("900128","新密码与旧密码相同"),
	E_900129("900129","登陆信息为空"),
	E_900130("900130","身份证号已存在"),
	E_900131("900131","今日已经发送十次，请明日再进行尝试"),
	E_900132("900132","请  秒后再尝试发送"),
	E_900133("900133","无用户绑定信息"),
	E_900134("900134","验证码超时"),
	E_900135("900135","用户绑定失败"),
	E_900136("900136","收货地址已达到上限!"),
	E_900137("900137","地址重复!"),

    ////////////////////////////////////业务级别异常详细编码 302xxx//////////////////////////  B模块


    /**
     * 系统异常
     */
    UNKNOWN("999999", "系统异常");

    @Getter
    @Setter
    String code;

    @Getter
    @Setter
    String desc;

    CommonErrorCode(boolean success, String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private CommonErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
