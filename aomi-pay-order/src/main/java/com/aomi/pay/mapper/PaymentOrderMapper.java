package com.aomi.pay.mapper;

import com.aomi.pay.entity.PaymentOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单交易信息 Mapper 接口
 * </p>
 *
 * @author hdq
 * @since 2020-08-07
 */
@Repository
public interface PaymentOrderMapper extends BaseMapper<PaymentOrder> {

    /**
     * @author  hdq
     * @date  2020/8/15
     * @desc  根据订单号修改订单信息
     **/
    int updateByOrderId(PaymentOrder paymentOrder);

    /**
     * @author  hdq
     * @date  2020/8/18
     * @desc  根据订单号修改订单信息
     **/
    List<PaymentOrder> queryListForPage(@Param("param")PaymentOrder paymentOrder, @Param("start")Integer start, @Param("pageSize")Integer pageSize);
}
