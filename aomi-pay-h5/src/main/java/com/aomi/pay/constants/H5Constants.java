package com.aomi.pay.constants;

/**
 * @Author hdq
 * @Date 2020/8/15
 * @Version 1.0
 */
public class H5Constants {

    /**
     *  user_agent  属性名
     */
    public static final String USER_AGENT_NAME = "user-agent";

    /**
     *  user_agent  支付宝value
     */
    public static final String USER_AGENT_ALIPAY = "AlipayClient";

    /**
     *  user_agent  微信value
     */
    public static final String USER_AGENT_WX = "MicroMessenger";

    /**
     *  支付宝auth_code
     */
    public static final String AUTH_CODE = "auth_code";

    /**
     *  微信code
     */
    public static final String CODE = "code";

    /**
     *  cookie 存货期限  一个月
     */
    public static final int COOKIE_MAX_AGE = 2592000;

    /**
     *  存放cookie的键
     */
    public static final String USER_ID_NAME = "userId";
}
