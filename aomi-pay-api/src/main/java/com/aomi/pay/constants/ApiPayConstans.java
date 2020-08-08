package com.aomi.pay.constants;

/**
 * @Author: hdq
 * @Description: 环迅常量constants
 * @Date: 2020/8/7
 * @Version: 1.0
 */
public class ApiPayConstans {

    /**
     * 环迅h5支付 service_id 支付宝
     */
    public static final String SERVICE_ID_ZFB = "hx.alipay.jspay";

    /**
     * 环迅h5支付 service_id 微信
     */
    public static final String SERVICE_ID_WX = "hx.wechat.jspay";

    /**
     * 环迅  结算周期  T+1
     */
    public static final String SETTLE_TYPE_T1 = "TONE";
    /**
     * 环迅  结算周期  T+0
     */
    public static final String SETTLE_TYPE_T0 = "TZERO";
    /**
     * 环迅  结算周期  D+0
     */
    public static final String SETTLE_TYPE_D0 = "DREAL";
    /**
     * 环迅  结算周期  S+0
     */
    public static final String SETTLE_TYPE_S0 = "TREAL";

}
