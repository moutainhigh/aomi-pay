package com.cloudbest.items.service;

import com.cloudbest.items.vo.PurchaseLimitVO;

/**
 * <p>
 * 限购规则表 服务类
 * </p>
 *
 * @author author
 * @since 2020/7/18 11:46
 */
public interface PurchaseLimitService {

    /**
     * Desc:
     * 〈添加限购规则〉
     * @date : 2020/7/18 14:03
     */
    public void add(PurchaseLimitVO purchaseLimitVO) throws Exception;

    /**
     * Desc:
     * 〈修改限购规则〉
     * @date : 2020/7/18 14:13
     */
    public void update(PurchaseLimitVO purchaseLimitVO);

    /**
     * Desc:
     * 〈根据商品id，skuid查询限购规则〉
     * @date : 2020/7/18 14:25
     */
    public PurchaseLimitVO getByItemIdSkuId(PurchaseLimitVO purchaseLimitVO);

    /**
     * Desc:
     * 〈根据商品id，skuid查询限购规则〉
     * @date : 2020/7/18 14:37
     */
    public void delete(PurchaseLimitVO purchaseLimitVO);

}
