package com.cloudbest.order.service;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.order.entity.ItemEntity;
import com.cloudbest.order.vo.ItemVO;
import com.cloudbest.order.vo.MainEntityVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单项信息 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Service
public interface ItemService {

    ItemEntity findById(Integer id)  throws BusinessException;

    ItemEntity createItemOrder(ItemEntity itemEntity);

    ItemEntity updateItemOrder(ItemEntity itemEntity);

    MainEntityVO secltItemsById(String id, String token);

    List<ItemEntity> queryList(ItemVO vo);

}
