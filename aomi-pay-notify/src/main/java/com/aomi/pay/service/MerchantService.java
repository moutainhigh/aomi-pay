package com.aomi.pay.service;


import com.aomi.pay.entity.MerchantAudioBind;

import java.util.List;

/**
 * 商户Service
 *
 * @author : hdq
 * @date 2020/8/18
 */
public interface MerchantService {

    /**
     * 根据商户id获取播报设备列表
     */
    List<MerchantAudioBind> queryAudiosByMerchantId(Long merchantId) throws Exception;

}
