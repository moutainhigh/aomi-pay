package com.aomi.pay.constants;

/**
 * @Author: hdq
 * @Description: 交易常量constants
 * @Date: 2020/8/4 15:41
 * @Version: 1.0
 */
public class PayConstants {

    /**
     * 收款方式  0：支付宝
     */
    public final static int PAY_TYPE_ZFB = 0;

    /**
     * 收款方式  0：支付宝
     */
    public final static String PAY_TYPE_ZFB_DESC = "支付宝";

    /**
     * 收款方式  1：微信
     */
    public final static int PAY_TYPE_WX = 1;

    /**
     * 收款方式  0：支付宝
     */
    public final static String PAY_TYPE_WX_DESC = "微信";

    /**
     * 收款方式  2：银联
     */
    public final static int PAY_TYPE_YL = 2;

    /**
     * 收款方式  0：支付宝
     */
    public final static String PAY_TYPE_YL_DESC = "银联";

    /**
     * 结算周期 默认 0：T+1
     */
    public final static int SETTLE_TYPE_T1 = 0;

    /**
     * 结算周期 默认 0：T+1
     */
    public final static String SETTLE_TYPE_T1_DESC = "T+1";

    /**
     * 结算周期   1：T+0
     */
    public final static int SETTLE_TYPE_T0 = 1;

    /**
     * 结算周期   1：T+0
     */
    public final static String SETTLE_TYPE_T0_DESC = "T+0";

    /**
     * 结算周期  2：S+0
     */
    public final static int SETTLE_TYPE_S0 = 2;

    /**
     * 结算周期  2：S+0
     */
    public final static String SETTLE_TYPE_S0_DESC = "S+0";

    /**
     * 结算周期  3：D+0
     */
    public final static int SETTLE_TYPE_D0 = 3;

    /**
     * 结算周期  3：D+0
     */
    public final static String SETTLE_TYPE_D0_DESC = "D+0";

    /**
     * 结算周期 4：D+1
     */
    public final static int SETTLE_TYPE_D1 = 4;

    /**
     * 结算周期 4：D+1
     */
    public final static String SETTLE_TYPE_D1_DESC = "D+1";

    /**
     * 交易状态 0：待支付
     */
    public final static int PAY_STATUS_TO_BE_PAY = 0;
    /**
     * 交易状态 1：已成功
     */
    public final static int PAY_STATUS_SUCCESS = 1;
    /**
     * 交易状态 2：失败
     */
    public final static int PAY_STATUS_FAIL = 2;
    /**
     * 交易状态 3：已关闭
     */
    public final static int PAY_STATUS_CLOSE = 3;
    /**
     * 交易状态 4：已退款
     */
    public final static int PAY_STATUS_REFUND = 4;

    /**
     * 环迅回调  交易状态 1：处理中
     */
    public final static String HX_PAY_STATUS_TO_BE_PAY = "1";
    /**
     * 环迅回调  交易状态 0：已成功
     */
    public final static String HX_PAY_STATUS_SUCCESS = "0";
    /**
     * 环迅回调  交易状态 4：失败
     */
    public final static String HX_PAY_STATUS_FAIL = "4";
    /**
     * 环迅回调  交易状态 9：已关闭
     */
    public final static String HX_PAY_STATUS_CLOSE = "9";
    /**
     * 环迅回调  交易状态 6：已退款
     */
    public final static String HX_PAY_STATUS_REFUND = "6";

}
