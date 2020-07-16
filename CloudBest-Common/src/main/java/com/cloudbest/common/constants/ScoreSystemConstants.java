package com.cloudbest.common.constants;

/**
 * @author hdq
 */
public class ScoreSystemConstants {
    /**
     * 积分系统网关   测试环境
     */
    //public static final String SCORE_URL = "http://10.103.1.2:8976";

    /**
     * 积分系统网关   生产环境
     */
    public static final String SCORE_URL = "http://172.19.73.77:8888";

    /**
     * 积分系统网关   本地环境
     */
    //public static final String SCORE_URL = "http://localhost:8976";

    /**
     * 积分系统接口  查询用户总购物券
     */
    public static final String SUM_SCORE = "/manager/query/sumScore";

    /**
     * 积分系统接口   增加积分
     */
    public static final String ADD_SCORE = "/cloudbest/cloudbest/addScore";
    /**
     * 积分系统接口 消费扣除积分
     */
    public static final String SUB_SCORE = "/manager/score/subScore";
    /**
     * 积分系统接口  友惠绑定用户
     */
    public static final String BIND_YH = "/manager/bind/yhBind";
    /**
     * 积分系统接口 友惠绑定用户绑定状态
     */
    public static final String BIND_YH_STATUS = "/manager/bind/yhBindStatus";
    /**
     * 积分系统接口  查询总积分变动列表
     */
    public static final String QUERY_SCORE_LIST = "/manager/query/queryList";
    /**
     * 积分系统接口  查询积分变动列表与总积分
     */
    public static final String QUERY_SCORERECORD_SUMSCORE = "/manager/users/scoreRecordAndSumScore";
}
