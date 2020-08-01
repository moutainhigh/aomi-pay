package com.aomi.pay.constants;

/**
 * @author hdq
 * @desc error_code 返回码,  error_info返回信息
 */
public class ResponseConstant {

    public static class ERR_CODE {

        /**
         * 成功 返回码
         */
        public static final String NORMAL = "0000";
        /**
         * 参数级别  通用异常返回码
         */
        public static final String PARAM = "1000";

        /**
         * 业务级别   通用异常返回码
         */
        public static final String BUSINESS = "2000";

        /**
         * 系统错误
         */
        public static final String FAIL = "9999";
    }

    public static class ERR_INFO {
        public static final String NORMAL = "成功";
        public static final String PARAM = "参数错误";
        public static final String NOMETHOD = "请求方法不存在";
        public static final String JSON_TO_BEAN_ERROR = "JSON报文解析出错";
        public static final String NOBIZDATA = "业务参数为空";
        public static final String NOBASEDATA = "基础参数为空";
        public static final String AUTHORIZATION_FAILED = "授权失败";
        public static final String ILLEGAL_PARAMETER = "请求参数校验失败";
        public static final String FAIL = "系统错误";
        public static final String AUTH_ERROR = "权限不足";
    }

    /**
     * User服务  详细返回信息
     */
    public static class ERR_INFO_USER extends ERR_INFO {
        public static final String NONE_ERROR = "用户不存在";
        public static final String DISABLE_ERROR = "用户已禁用";
        public static final String DELETED_ERROR = "用户已删除";

    }

    /**
     * page 返回码
     */
    public static class ERR_CODE_PAGE extends ERR_CODE {
        public static final String SIZE_ERROR = "10";
        public static final String NO_ERROR = "10";
        public static final String NO_OVERSIZE = "10";

    }

    /**
     * 参数级别  通用 返回信息
     */
    public static class ERR_INFO_COMMON_PARAM extends ERR_INFO {
        public static final String ANALYSIS_ERROR = "解析错误";
        public static final String NONE_ERROR = "不能为空";
        public static final String HAS_SPECIALCHAR_ERROR = "不能含有特殊字符";
        public static final String HAS_CHINESE_ERROR = "不能含有中文字符";
        public static final String FORMAT_ERROR = "格式不正确";
    }

}
