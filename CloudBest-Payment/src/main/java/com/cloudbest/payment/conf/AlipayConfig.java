package com.cloudbest.payment.conf;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "";
    // 私钥 pkcs8格式的         有支付宝提供工具==支付宝开放平台开发助手==生成
    public static String RSA_PRIVATE_KEY = "";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问,后端处理
    public static String notify_url = "https://www.***.com/pay_s";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
//	public static String return_url = "";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz6E8KWmPjRfxtg1X3twnPdkRUd6rwCsCtUfI8OTsyz+Ir3cPKd5yXhnAdhUS14ODXGCadCRTbH7MHTialAKsiab0tEE0BM6/MT2iYYU5pbx978HNk/HTOqFwnz1eh8XpAWe+BQS4aIujmOIxZ49IqixsLTA3GC60aeLnqKwNtZ3TXdjmVWEF02dKNdVV0Zaq45LAOnYCjQF1lRbt4qHaYCoG1fqNO+vob1bdtv/ubXHcfgVOh+nWa/c25Kq0Ne90CRfNj3z9IRiTJoN7lssMK6wrbwTmzqcs3E9jnkFcr0W2SL1InIpTNY15XOOYnGAHFeoSrOJMYEeEjyD3NIc/swIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

}
