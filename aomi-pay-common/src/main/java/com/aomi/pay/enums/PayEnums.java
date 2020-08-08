package com.aomi.pay.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 交易相关enum
 *
 * @author dd
 * @date 2020-08-07
 */
public class PayEnums {

    /**
     * 结算周期 默认0：T+1  1：T+0  2：S+0 3：D+0 4：D+1
     */
    public final static int SETTLE_TYPE_T1 = 0;
    public final static int SETTLE_TYPE_T0 = 1;
    public final static int SETTLE_TYPE_S0 = 2;
    public final static int SETTLE_TYPE_D0 = 3;
    public final static int SETTLE_TYPE_D1 = 4;

    /**
     * 结算周期 默认0：T+1  1：T+0  2：S+0 3：D+0 4：D+1
     */
    public enum EnumSettleType {

        T1(SETTLE_TYPE_T1, "TONE"),
        T0(SETTLE_TYPE_T0, "TZERO"),
        S0(SETTLE_TYPE_S0, "TREAL"),
        D0(SETTLE_TYPE_D0, "DREAL"),
        D1(SETTLE_TYPE_D1, ""),
        ;
        @Getter
        @Setter
        private int code;
        @Getter
        @Setter
        private String msg;

        EnumSettleType(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
