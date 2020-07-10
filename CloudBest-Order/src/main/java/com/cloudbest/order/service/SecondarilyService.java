package com.cloudbest.order.service;

import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.vo.SecondarilyVO;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface SecondarilyService {

    int createSecondarilyOrder(SecondarilyEntity secondarilyEntity);

    SecondarilyEntity selectByid(Integer id);

    SecondarilyEntity updateSecondarilyOrder(SecondarilyEntity secondarilyEntity);

    void selectUserInfoById(Long id);

    SecondarilyEntity selectSecondarilyOrderById(String id);

    List<String> selectDeliveryStatus(String id);

    SecondarilyEntity offSecondarily(Integer id);

    List<SecondarilyEntity> queryList(SecondarilyVO vo);
}
