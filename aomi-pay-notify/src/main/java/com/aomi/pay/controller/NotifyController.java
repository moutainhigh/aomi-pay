package com.aomi.pay.controller;


import com.aomi.pay.MessageVO;
import com.aomi.pay.config.DbContextHolder;
import com.aomi.pay.entity.MerchantAudioBind;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.enums.DBTypeEnum;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.service.NotifyService;
import com.aomi.pay.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易类接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "NotifyController", tags = "交易接口管理")
public class NotifyController {

    /**
     * 区域id
     */
    private static String REGION_ID;

    @Value("${region_id}")
    public void setRegionId(String regionId) {
        REGION_ID = regionId;
    }

    /**
     * key
     */
    private static String ACCESS_KEY;

    @Value("${access_key}")
    public void setAccessKey(String accessKey) {
        ACCESS_KEY = accessKey;
    }

    /**
     * 秘钥
     */
    private static String ACCESS_SECRET;

    @Value("${access_secret}")
    public void setAccessSecret(String accessSecret) {
        ACCESS_SECRET = accessSecret;
    }

    /**
     * 产品key
     */
    private static String PRODUCT_KEY;

    @Value("${product_key}")
    public void setProductKey(String productKey) {
        PRODUCT_KEY = productKey;
    }

    /**
     * 传输质量等级
     */
    private static int QOS;

    @Value("${qos}")
    public void setQos(int qos) {
        QOS = qos;
    }

    @Resource
    private NotifyService paymentOrderService;

    @Resource
    private MerchantService merchantService;

    @ApiOperation(value = "支付回调")
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);

        JSONObject jsonObject = JSONObject.fromObject(request);
        Object data = jsonObject.get("data");
        if (!StringUtils.isEmpty(data)) {
            NotifyRequest notifyRequest = GeneralConvertorUtil.convertor(JSONObject.fromObject(data), NotifyRequest.class);
            //进事务前手动切数据源
            DbContextHolder.setDbType(DBTypeEnum.order);
            PaymentOrder paymentOrder = paymentOrderService.payNotify(notifyRequest);

            try {
                DbContextHolder.setDbType(DBTypeEnum.user);
                List<MerchantAudioBind> merchantAudioBinds = merchantService.queryAudiosByMerchantId(paymentOrder.getMerchantId());
                if (!merchantAudioBinds.isEmpty()) {
                    for (MerchantAudioBind merchantAudioBind : merchantAudioBinds) {
                        NotifyUtil.send(merchantAudioBind.getAudioCode(), merchantAudioBind.getAudioType(), paymentOrder.getPayType(), paymentOrder.getAmount());
                    }
                }
            } catch (Exception e) {
                log.error("播报异常:", e);
                return "SUCCESS";
            }

        }
        log.info("--------支付回调结束--------");
        return "SUCCESS";
    }

}
