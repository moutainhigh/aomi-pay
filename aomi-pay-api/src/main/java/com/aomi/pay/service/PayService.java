package com.aomi.pay.service;

import com.aomi.pay.dto.JsPayDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调用环迅接口支付
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Transactional(rollbackFor = Exception.class)
public interface PayService {

    /**
     * h5支付
     */
    Object onlineTrade(JsPayDTO jsPayDTO) throws Exception;

}
