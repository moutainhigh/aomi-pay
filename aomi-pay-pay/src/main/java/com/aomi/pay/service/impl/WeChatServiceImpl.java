package com.aomi.pay.service.impl;

import com.aomi.pay.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * 微信Service实现类
 *
 * @author : hdq
 * @date 2020/8/14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class WeChatServiceImpl implements WeChatService {

    /**
     * 微信appid
     */
    @Value("${wx.appid}")
    private String appid;

    /**
     * 微信secret
     */
    @Value("${wx.secret}")
    private String secret;

    /**
     * @author hdq
     * @date 2020/8/14
     * @desc 获取微信openId
     **/
    @Override
    public String getOpenId(String code) throws Exception {

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=".concat(appid).concat("&secret=".concat(secret).concat("&code=".concat(code).concat("&grant_type=authorization_code")));
        RestTemplate restTemplate = new RestTemplate();
        String subScoreResult = restTemplate.getForObject(url, String.class);
        JSONObject subScoreObject = JSONObject.fromObject(subScoreResult);
        String openId = subScoreObject.getString("openid");
        log.info("subScoreObject:{}",subScoreObject);
        return openId;
    }
}



























