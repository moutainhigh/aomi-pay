package com.cloudbest.items.service;

import com.cloudbest.items.entity.Stock;
import com.cloudbest.items.vo.ItemsStockVO;
import com.cloudbest.items.vo.SkuLockVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库存表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface StockService {

    Stock createNewStock(Stock info);

    Stock updateStock(Stock info);

    void deleteStock(Integer stockId);

    List<Stock> queryStock(ItemsStockVO vo);

    Map<String,Object> selecStockBySkuId(Integer id);

    String checkAndLockStock(List<SkuLockVO> skuLockVOS);

    void test(Integer id, Integer num);

    void test2(Integer id, Integer num);

    Stock updateSaleVolume(Integer id, Integer num);

    Stock offStock(Integer id);

    Stock onStock(Integer id);

    void unLockStock(String orderToken);

    Stock onOffStock(Integer id, Integer status);
}
