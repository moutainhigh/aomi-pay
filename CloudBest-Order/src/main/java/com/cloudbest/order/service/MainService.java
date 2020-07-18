package com.cloudbest.order.service;

import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.vo.MainEntityVO;
import com.cloudbest.order.vo.OrderMainVO;
import com.cloudbest.order.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Service
public interface MainService {

    /*
     * 订单主表添加
     * */
    int createMainOrder(MainEntity mainVo);

    MainEntity selectByid(Integer id);

    MainEntity selectMainOrderById(String id);

    void deleteMainOrder(String id);

    List<MainEntityVO> selectAllOrder(OrderMainVO vo, String token);

    List<MainEntity> queryList(OrderMainVO vo);

    MainEntity offMain(Integer id);

    void editMain(OrderSubmitVO orderSubmitVO);

    List<MainEntityVO> selectAllStatus(OrderMainVO status, String token);

    MainEntity updateOrderAfterPay(String orderId/*, Integer status*/);


}
