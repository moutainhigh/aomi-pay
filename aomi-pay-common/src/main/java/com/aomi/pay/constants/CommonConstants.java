package com.aomi.pay.constants;

public class CommonConstants {
    /**
     * 参数错误
     */
    public static final String ERROR_PARAM = "000001";
    /**
     * 参数不完整
     */
    public static final String ERROR_VERI_CODE = "000002";
    /**
     * 通道错误
     */
    public static final String ERROR_PASS_ERROR = "000003";
    /**
     * 支付错误
     */
    public static final String ERROR_PAY_PASS = "000004";
    /**
     * 密令错误
     */
    public static final String ERROR_TOKEN = "000005";

    /**
     * 成功
     **/
    public static final String SUCCESS = "000000";

    public static final String BINDTASKCARD = "888888";
    /***失败**/
    public static final String FALIED = "999999";
    /**
     * 等待回调返回
     **/
    public static final String WAIT_NOTIFY = "999998";
    /**
     * 短信确认调用
     **/
    public static final String CKSMS_CODE = "999990";
    /**
     * 等待处理
     **/
    public static final String WAIT_CHECK = "666666";

    /***秘密密钥**/
    public static final String SECRETKEY = "juhe-20170328";
    /**
     * 结果
     **/
    public static final String RESULT = "result";
    /**
     * 有效
     **/
    public static final String STATUS_VALID = "0";
    /**
     * 无效
     **/
    public static final String STATUS_INVALID = "1";
    /***返回码**/
    public static final String RESP_CODE = "resp_code";
    /***返回描述**/
    public static final String RESP_MESSAGE = "resp_message";


    /**
     * 参数级别  通用 code
     */
    public static class ERR_CODE {
    }

    /**
     * 参数级别  通用 desc
     */
    public static class ERR_DESC {
    }

    /**
     * 参数级别  通用 desc
     */
    public static class ERR_CODE_PARAM extends ERR_CODE{
        public static final int ANALYSIS_ERROR = 999001;
        public static final int NONE_ERROR = 999002;
        public static final int HAS_SPECIALCHAR_ERROR = 999003;
        public static final int HAS_CHINESE_ERROR = 999004;
        public static final int FORMAT_ERROR = 999005;
    }

    /**
     * 参数级别  通用 desc
     */
    public static class ERR_DESC_PARAM extends ERR_DESC{
        public static final String ANALYSIS_ERROR = "解析错误";
        public static final String NONE_ERROR = "不能为空";
        public static final String HAS_SPECIALCHAR_ERROR = "不能含有特殊字符";
        public static final String HAS_CHINESE_ERROR = "不能含有中文字符";
        public static final String FORMAT_ERROR = "格式不正确";
    }
}
