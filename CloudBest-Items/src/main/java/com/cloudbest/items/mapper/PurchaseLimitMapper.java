package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.PurchaseLimit;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 限购规则表 Mapper 接口
 * </p>
 *
 * @author hdq
 * @since 2020/7/18 11:46
 */
@Repository
public interface PurchaseLimitMapper extends BaseMapper<PurchaseLimit> {

    /**
     * 根据itemsID,skuId 查询限购规则
     */
    PurchaseLimit selectByItemIdSkuId(PurchaseLimit purchaseLimit);

    @Override
    int updateById(PurchaseLimit purchaseLimit);

}
