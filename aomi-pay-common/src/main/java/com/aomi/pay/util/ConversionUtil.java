package com.aomi.pay.util;

import com.aomi.pay.constants.PayConstants;

/**
 * @Author hdq
 * @Date 2020/8/18
 * @Version 1.0
 * @Desc 字段转化描述工具类
 */
public class ConversionUtil {

    /**
     * @author hdq
     * @date 2020/8/18
     * @desc 交易类型码 转成 描述
     **/
    public static String convertPayStatus(int payType) {
        String desc = "";
        if (payType == PayConstants.PAY_TYPE_ZFB) {
            desc = PayConstants.PAY_TYPE_ZFB_DESC;
        } else if (payType == PayConstants.PAY_TYPE_WX) {
            desc = PayConstants.PAY_TYPE_WX_DESC;
        } else if (payType == PayConstants.PAY_TYPE_YL) {
            desc = PayConstants.PAY_TYPE_YL_DESC;
        }
        return desc;
    }

    /**
     * @author  hdq
     * @date  2020/8/18
     * @desc 结算周期 转成 描述
     **/
    public static String convertSettleType(int settleType) {
        String desc = "";
        switch (settleType) {
            case PayConstants.SETTLE_TYPE_T1:
                desc = PayConstants.SETTLE_TYPE_T1_DESC;
                break;
            case PayConstants.SETTLE_TYPE_T0:
                desc = PayConstants.SETTLE_TYPE_T0_DESC;
                break;
            case PayConstants.SETTLE_TYPE_S0:
                desc = PayConstants.SETTLE_TYPE_S0_DESC;
                break;
            case PayConstants.SETTLE_TYPE_D0:
                desc = PayConstants.SETTLE_TYPE_D0_DESC;
                break;
        }

        return desc;
    }
}
