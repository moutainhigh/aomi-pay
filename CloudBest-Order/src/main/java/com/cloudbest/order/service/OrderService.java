package com.cloudbest.order.service;


import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.vo.OrderConfirmVO;
import com.cloudbest.order.vo.OrderSubmitResponseVO;
import com.cloudbest.order.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface OrderService {
    OrderConfirmVO confirm(Map<Integer,Integer> cartMap,String token);
    OrderSubmitResponseVO crateOrder(OrderSubmitVO orderSubmitVO);
    OrderSubmitResponseVO submit(OrderSubmitVO orderSubmitVO);
    String crateAliPay();
    void payByScore(MainEntity mainEntity);

    OrderSubmitResponseVO submitTwo(OrderSubmitVO orderSubmitVO);


//    void payFail(MainEntity mainEntity);
//    String crateAliPay();
//    void paySuccess(AlipayBean alipayBean ,Integer status);
}
