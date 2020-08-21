package com.aomi.pay.mapper.order;

import com.aomi.pay.entity.PaymentOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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
     * @Desc 根据订单号修改订单信息
     **/
    void updateByOrderId(PaymentOrder paymentOrder);

}
