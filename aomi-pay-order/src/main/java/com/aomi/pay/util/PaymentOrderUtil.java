package com.aomi.pay.util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author hdq
 * @Date 2020/8/7 16:44
 * @Desc 根据时间戳+随机数生成订单号  共20位
 * @Version 1.0
 */
public class PaymentOrderUtil {
    /**
     * 订单类别头
     */

    private static final String ORDER_CODE = "1";

    /**
     * 退款类别头
     */
    private static final String REFUND_ORDER = "2";

    /**
     * 随机编码
     */
    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};

    /**
     * 用户id和随机数总长度
     */
    private static final int MAXLENGTH = 4;

    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Integer userId) {
        String idStr = userId.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(MAXLENGTH - idStr.length())).toString();
    }

    /**
     * 生成时间戳  取YYMMDDHHMMssSSS
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());
        System.out.println(date);
        return date.substring(2, date.length());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */

    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        return (((long) (new Random().nextDouble() * (max - min)))) + min;
    }


    /**
     * 生成不带类别标头的编码
     */
    private static synchronized String getCode() {
        return getDateTime() + getRandom(MAXLENGTH);
    }


    /**
     * 生成订单单号编码(调用方法)
     */
    public static BigInteger getOrderCode() {
        return new BigInteger(ORDER_CODE + getCode());
    }


    /**
     * 生成退款单号编码(调用方法)
     */
    public static String getRefundCode() {
        return REFUND_ORDER + getCode();
    }

    public static void main(String[] args) {
        System.out.println(getOrderCode());
    }
}
