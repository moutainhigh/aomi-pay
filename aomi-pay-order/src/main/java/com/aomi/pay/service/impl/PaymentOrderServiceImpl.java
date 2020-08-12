package com.aomi.pay.service.impl;

import com.aomi.pay.constants.ApiConstans;
import com.aomi.pay.constants.PayConstans;
import com.aomi.pay.dto.hx.JsPayDTO;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.feign.ApiClient;
import com.aomi.pay.mapper.PaymentOrderMapper;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.DateUtil;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.PaymentOrderUtil;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    @Resource
    private ApiClient apiClient;

    /**
     * 支付回调地址
     */
    @Value("${pay.hx.notify_url}")
    private String notifyUrl;

    /**
     * 结算周期
     */
    @Value("${pay.hx.settle_type}")
    private int settleType;

    /**
     * 结算周期
     */
    @Value("${pay.hx.exprice}")
    private int exprice;

    /**
     * h5支付 商户id，金额，支付类型
     *
     * @return 原生js信息
     */
    @Override
    public String jsPay(Long merchantId, BigDecimal amount, int payType) throws Exception {
        //根据商户号获取需要的商户信息 TODO 暂时写死  redis
        String subAppid = "wx57f9d11132fc79c1";
        String userId = "oXpzSv9AcnTkxsErnGUKCDzZIZBs";//测试用 微信
        //String userId = "2021001168632988";//测试用 微信
        String platformMerchantId = "015440309175982";
        String subject = "test华一炒粉---收款";//TODO  商户名+收款
        String merchantNo = "015440309175982";
        Integer productCode = 100011;//微信
        //String productCode = "100010";//支付宝
        Long agentId = Long.valueOf("10000000001");//TODO 商户下有地推人员id，根据地推人员id 查询

        //生成交易订单号
        BigInteger orderId = PaymentOrderUtil.getOrderCode();

        JsPayDTO jsPayDTO = new JsPayDTO();
        jsPayDTO.setOrderId(orderId);
        jsPayDTO.setAmount(amount);
        jsPayDTO.setPayType(payType);
        jsPayDTO.setPlatformMerchantId(merchantNo);
        //产品费率编码  //TODO 暂时先写死  查商户开通了哪些产品 根据支付类型选择  取出redis 或者 查询环迅平台接口
        if (payType == PayConstans.PAY_TYPE_ZFB) {
            jsPayDTO.setProductCode(productCode);
        } else if (payType == PayConstans.PAY_TYPE_WX) {
            jsPayDTO.setProductCode(productCode);
            //微信需要传subAppid
            jsPayDTO.setSubAppid(subAppid);
        } else if (payType == PayConstans.PAY_TYPE_YL) {
            jsPayDTO.setProductCode(productCode);
        }
        jsPayDTO.setUserId(userId);
        jsPayDTO.setExprice(exprice);
        jsPayDTO.setPlatformMerchantId(platformMerchantId);//平台商户号
        jsPayDTO.setNotifyUrl(notifyUrl);
        jsPayDTO.setSettleType(settleType);//TODO 结算周期  暂T1 读配置
        jsPayDTO.setSubject(subject);
        //调用环迅api接口
        BaseResponse response = apiClient.onlineTrade(jsPayDTO);
        log.info("环迅api：{}", response);
        JSONObject jsonObject = JSONObject.fromObject(response.getData());
        //TODO  code不为100000
        //实体转化
        PaymentOrder paymentOrder = GeneralConvertorUtil.convertor(jsPayDTO, PaymentOrder.class);
        paymentOrder.setMerchantId(merchantId);//机构商户号
        paymentOrder.setAgentId(agentId);

        paymentOrder = objectToPaymenOrder(paymentOrder, jsonObject);
        log.info("paymentOrder:{}", paymentOrder);
        //插入订单交易记录
        paymentOrderMapper.insert(paymentOrder);

        return paymentOrder.getPayInfo();
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 实体转化
     **/
    private PaymentOrder objectToPaymenOrder(PaymentOrder paymentOrder, JSONObject jsonObject) {
        paymentOrder.setPlatformMerchantId(jsonObject.getString(ApiConstans.MERCHANT_NO_NAME));//平台商户号
        if (jsonObject.has(ApiConstans.TEADE_NO_NAME) && StringUtil.isNotBlank(jsonObject.getString(ApiConstans.TEADE_NO_NAME))) {
            paymentOrder.setPlatformOrderId(jsonObject.getString(ApiConstans.TEADE_NO_NAME));//平台订单号
        }
        //平台支付状态转换系统支付状态
        if (jsonObject.has(ApiConstans.TRADE_STATUS_NAME)) {
            paymentOrder.setPayStatus(tradeStatusToPayStatus(jsonObject.getString(ApiConstans.TRADE_STATUS_NAME)));
        }
        if (jsonObject.has(ApiConstans.TRADE_TIME_NAME)) {
            paymentOrder.setCreateTime(DateUtil.format(jsonObject.getString(ApiConstans.TRADE_TIME_NAME), DateUtil.YYYYMMDDHHMMSS));
        }
        if (jsonObject.has(ApiConstans.PAY_INFO_NAME)) {
            paymentOrder.setPayInfo(jsonObject.getString(ApiConstans.PAY_INFO_NAME));
        }
        return paymentOrder;
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 平台支付状态转换系统支付状态
     **/
    private int tradeStatusToPayStatus(String tradeStatus) {
        int payStatus = PayConstans.PAY_STATUS_TO_BE_PAY;//默认待支付
        switch (tradeStatus) {
            case ApiConstans.TRADE_STATUS_SUCCESS:
                payStatus = PayConstans.PAY_STATUS_SUCCESS;
                break;
            case ApiConstans.TRADE_STATUS_PROCESSING:
                payStatus = PayConstans.PAY_STATUS_TO_BE_PAY;
                break;
            case ApiConstans.TRADE_STATUS_FAIL:
                payStatus = PayConstans.PAY_STATUS_FAIL;
                break;
            case ApiConstans.TRADE_STATUS_CLOSE:
                payStatus = PayConstans.PAY_STATUS_CLOSE;
                break;
            case ApiConstans.TRADE_STATUS_REFUND:
                payStatus = PayConstans.PAY_STATUS_REFUND;
                break;
            default:
                payStatus = PayConstans.PAY_STATUS_TO_BE_PAY;
        }
        return payStatus;
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @Param tradeStatus 支付状态， platformMerchantId 平台商户号  ， tradeNo 平台流水号，  outTradeNo系统交易id
     * @desc 支付回调
     */
    @Override
    public String payNotify(String tradeStatus, String platformMerchantId, String tradeNo, String outTradeNo) throws Exception {
        //TODO  逻辑待添加
        return null;
    }
}



























