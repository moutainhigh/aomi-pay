package com.cloudbest.items.service;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Promotion;
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
public interface PromotionService {

    List<Map<String, Object>> queryPromotionItems(String ids);

    Promotion queryPromotionFirst();
}
