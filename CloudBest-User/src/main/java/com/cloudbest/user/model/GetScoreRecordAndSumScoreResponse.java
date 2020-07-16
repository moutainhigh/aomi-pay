package com.cloudbest.user.model;

import com.cloudbest.user.vo.ScoreSourceVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: hdq
 * @Description: 积分变动记录和总积分-返回封装
 * @Date: 2020/7/16 16:18
 * @Version: 1.0
 */
@Data
public class GetScoreRecordAndSumScoreResponse {

    /**总积分*/
    private BigDecimal SumScore;

    /**积分变动记录*/
    private List<ScoreSourceVO> scoreList;
}
