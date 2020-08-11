package com.aomi.pay.constants;

/**
 * @Author: hdq
 * @Description: 平台常量constants
 * @Date: 2020/8/4 15:41
 * @Version: 1.0
 */
public class ApiConstans {

    /**
     * 环迅平台处理结果  成功 1
     */
    public static final String RESULT_CODE_SUCCESS = "1";

    /**
     * 环迅平台处理结果  失败 0
     */
    public static final String RESULT_CODE_FAIL = "0";

    /**
     * 环迅平台返回属性  处理结果 属性名
     */
    public static final String RESULT_CODE_NAME = "resultCode";

    /**
     * 环迅平台返回属性  错误码   属性名
     */
    public static final String ERROR_CODE_NAME = "errorCode";

    /**
     * 环迅平台返回属性  错误描述  属性名
     */
    public static final String ERROR_DESC_NAME = "errorDesc";

    /**
     * 环迅平台返回属性  主体Data  属性名
     */
    public static final String ERROR_DESC_DATA = "data";

    /**
     * 环迅平台返回属性  交易状态  属性名
     */
    public static final String TRADE_STATUS_NAME = "tradeStatus";

    /**
     * 环迅平台返回属性  交易时间  属性名
     */
    public static final String TRADE_TIME_NAME = "tradeTime";
    /**
     * 环迅平台返回属性  原生js信息  属性名
     */
    public static final String PAY_INFO_NAME = "payInfo";
    /**
     * 环迅平台返回属性  平台交易号  属性名
     */
    public static final String TEADE_NO_NAME = "tradeNo";
    /**
     * 环迅平台返回属性  平台交易号  属性名
     */
    public static final String MERCHANT_NO_NAME = "merchantNo";
    /**
     * 环迅平台返回属性  外部流水号  属性名
     */
    public static final String OUT_TRADE_NO = "outTradeNo";


    /**
     * 环迅平台返回属性  交易状态  成功  0
     */
    public static final String TRADE_STATUS_SUCCESS = "0";
    /**
     * 环迅平台返回属性  交易状态  处理中  1
     */
    public static final String TRADE_STATUS_PROCESSING = "1";
    /**
     * 环迅平台返回属性  交易状态  失败  4
     */
    public static final String TRADE_STATUS_FAIL = "4";
    /**
     * 环迅平台返回属性  交易状态  已退款  6
     */
    public static final String TRADE_STATUS_REFUND = "6";
    /**
     * 环迅平台返回属性  交易状态  已关闭  9
     */
    public static final String TRADE_STATUS_CLOSE = "9";


}
