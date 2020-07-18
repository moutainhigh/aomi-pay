package com.cloudbest.items.service;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.vo.ItemsInfoVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品详情表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface ItemsInfoService {

    ItemsInfo createNewItem(ItemsInfo itemsInfo);

    ItemsInfo updateItems(ItemsInfo info);

    void deleteItems(Integer itemId);

    List<Map<String,Object>> queryItems(ItemsInfoVO vo);

    ItemsInfo offItems(Integer itemId);

    ItemsInfo getItemById(Integer itemId);

    Map<String, Object> getItemInfo( Integer itemId);

    ItemsInfoVO getItemInfoById( Integer itemId);

    Map<String, Object> getItemInfoSku(Integer skuId, Integer itemId);

    Map<String, BigDecimal> totalItemsPrice(Integer skuId, Integer itemId, Integer count) throws BusinessException;

    List<Map<String, Object>> queryFavoriteItems();

    List<ItemsInfo> queryItemsByName(Integer itemId,String name);

    ItemsInfo onItems(Integer id);

    ItemsInfo onOffItems(Integer id, Integer isView);

    List<Map<String, Object>> queryTopQualityItems();


    ItemsInfo queryItemsById(Integer spuId);
}
