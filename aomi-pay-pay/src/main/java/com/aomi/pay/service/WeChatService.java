package com.aomi.pay.service;

import org.springframework.transaction.annotation.Transactional;


/**
 * 微信Service
 *
 * @author : hdq
 * @date 2020/8/14
 */
@Transactional(rollbackFor = Exception.class)
public interface WeChatService {

    /**
     * @author hdq
     * @date 2020/8/14
     * @desc 获取微信openId
     **/
    String getOpenId(String code) throws Exception;

}
