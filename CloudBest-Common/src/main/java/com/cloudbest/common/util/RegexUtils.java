package com.cloudbest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hdq
 * @date 2020/7/15 13:44
 * @desc  正则工具类
 */
@Slf4j
public class RegexUtils {

    /** ip*/
    public static final String REGEX_IP = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    /** 邮件*/
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /** 中文*/
    public static final String REGEX_CHINESE = "[\u4e00-\u9fa5]";

    /** 数字*/
    public static final String REGEX_NUMBER = "[0-9]*";

    /** 不含特殊字符*/
    public static final String REGEX_SPECIALCHAR = "[a-z]*[A-Z]*\\d*-*_*\\s*";
    /** 手机号*/
    public static final String REGEX_MOBILE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    /** 性别*/
    public static final String REGEX_SEX = "[1-3]";

    /** 页码*/
    public static final String REGEX_PAGENO = "[1-9]\\d{0,9}"; //TODO 暂定10位数字
    /** 页面大小*/
    public static final String REGEX_PAGESIZE = "[1-9]\\d{0,9}"; //TODO 暂定10位数字
    /** 服务类型*/
    public static final String REGEX_SERVERTYPE = "[1-4]";

	public static final String REGEX_ARTICLE_TYPE = "[1-2]";//资讯类别
    /**公司规模（A:微型企业（20人以下）；B:小型企业(20-99人)；C:中型企业(100-299人)；D:大型企业(300人以上)；)*/
    public static final String REGEX_COMPANY_SCOPE = "[A-D]";
    /** 是否默认*/
    public static final String REGEX_DEFAULT = "[0-1]";
    /** 是否存在*/
    public static final String REGEX_ISEXIST = "[0-1]";//资讯类别
    /** 支付方式 */
    public static final String REGEX_PAYTYPE = "[1-4]";
    /** 金额 */
    public static final String REGEX_AMT = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    /** 时间  yyyy-mm-dd HH:mm:ss */
    public static final String REGEX_TIME_YMDHMS = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3])):([0-5]?[0-9])((\\s)|(:([0-5]?[0-9])))))?$";
    /** 图片文件后缀 */
    public static final String REGEX_PICTURE = "\\.(jpg|gif|jpeg|png|bmp)+$";
    /** 时间  yyyy-mm-dd */
    public static final String REGEX_DATE_YYYYMMDD ="^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /** 时间  yyyy-mm */
    public static final String REGEX_DATE_YYYYMM ="^(19|20)[0-9]{2}-(0[1-9]|1[012])$";
    /** 按钮  保存 0  提交 1 */
    public static final String REGEX_BUTTON = "[0-1]";
    /** 是否为商户固定角色  0：否 ，1：是  */
    public static final String REGEX_ISFIXED = "[0-1]";
    /** 角色区域(1:企业运营，2:加盟商户，3:合作客户)  */
    public static final String REGEX_ROLEAREA = "[1-3]";
    /** 站内信类型(1:系统通知，2:服务通知)  */
    public static final String REGEX_ZNX_TYPE = "[1-2]";
    /** 站内信终端(1:WEB，2:APP)  */
    public static final String REGEX_ZNX_CLIENT = "[1-2]";
    /** 图片base64正则 */
    public static final String REGEX_PICTURE_BASE64 = "data:image";
    /** 支付方式 */
    public static final String REGEX_PAY_TYPE = "[1-4]";
    /** 交易类型 */
    public static final String REGEX_TRANSACTION_TYPE = "01|02";
    /**
     * 判断是否是正确的IP地址
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        return ip.matches(REGEX_IP);
    }

    /**
     * 判断是否是正确的邮箱地址
     *
     * @param email
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return email.matches(REGEX_EMAIL);
    }

    /**
     * 判断是否含有中文，仅适合中国汉字，不包括标点
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_CHINESE);
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * 判断是否正整数
     * @param number
     *            数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return number.matches(REGEX_NUMBER);
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal
     *            数字
     * @param count
     *            小数位数
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count) {
        if (StringUtils.isBlank(decimal)) {
            return false;
        }
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count
                + "})?$";
        return decimal.matches(regex);
    }

    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber
     *            手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return false;
        }
        return phoneNumber.matches(REGEX_MOBILE);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        if (text.replaceAll(REGEX_SPECIALCHAR, "").length() == 0) {
            // 如果不包含特殊字符
            return true;
        }
        return false;
    }

    /**
     * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
     */
    public static boolean isChinese2(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 时间 YYYY-MM-DD HH:mm:ss
     */
    public static boolean isTimeYMDHMS(String str){
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 图片后缀名
     */
    public static boolean isPicture(String str){
        return str.matches(REGEX_PICTURE);
    }

    public static boolean isPictureBase64(String str){
        if(str.indexOf(REGEX_PICTURE_BASE64)!=-1){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String [] args){
        BigDecimal a = new BigDecimal("10.5654");

    }


}
