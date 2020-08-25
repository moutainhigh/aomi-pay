package com.aomi.pay.service.impl;

import com.aomi.pay.constants.ApiConstants;
import com.aomi.pay.constants.PayConstants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.dto.hx.JsPayDTO;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.feign.ApiClient;
import com.aomi.pay.mapper.order.PaymentOrderMapper;
import com.aomi.pay.model.ProductResponse;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.*;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.JsPayVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/18
 */
@Slf4j
@Service
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
     * 订单失效时间
     */
    @Value("${pay.hx.exprice}")
    private int exprice;

    /**
     * 微信appid
     */
    @Value("${wx.appid}")
    private String appid;

    /**
     * h5支付 商户id，金额，支付类型
     *
     * @return 原生js信息
     */
    @Override
    public String jsPay(JsPayVO jsPayVO) throws Exception {

        Long merchantId = jsPayVO.getMerchantId();
        //平台商户号
        String platformMerchantId = jsPayVO.getPlatformId();
        //TODO  商户名+收款
        String subject = jsPayVO.getSimpleName().concat(" 收款");
        String bdNo = jsPayVO.getBdNo();
        //生成交易订单号
        BigInteger orderId = PaymentOrderUtil.getOrderCode();
        int payType = jsPayVO.getPayType();
        JsPayDTO jsPayDTO = new JsPayDTO();
        jsPayDTO.setOrderId(orderId);
        jsPayDTO.setAmount(jsPayVO.getAmount());
        jsPayDTO.setPayType(payType);
        //产品费率编码
        List<ProductResponse> productResponses = jsPayVO.getProductList();
        String productCode = getProductByPayType(payType, productResponses);
        if (StringUtil.isBlank(productCode)) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301015);
        }

        jsPayDTO.setProductCode(productCode);
        jsPayDTO.setUserId(jsPayVO.getUserId());

        if (payType == PayConstants.PAY_TYPE_WX) {
            //微信需要传subAppid
            jsPayDTO.setSubAppid(appid);
        }

        jsPayDTO.setExprice(exprice);
        //平台商户号
        jsPayDTO.setPlatformMerchantId(platformMerchantId);
        jsPayDTO.setNotifyUrl(notifyUrl);
        //TODO 结算周期  暂D0 如果失败重新下单 T1
        jsPayDTO.setSettleType(PayConstants.SETTLE_TYPE_T1);
        jsPayDTO.setSubject(subject);
        //调用环迅api接口
        BaseResponse response = apiClient.onlineTrade(jsPayDTO);
        log.info("环迅h5-api：{}", response);
        if (!CommonErrorCode.SUCCESS.getCode().equals(response.getCode())) {
            CommonExceptionUtils.throwBusinessException(response.getCode(), response.getMessage());
        }
        JSONObject jsonObject = JSONObject.fromObject(response.getData());
        //实体转化
        PaymentOrder paymentOrder = GeneralConvertorUtil.convertor(jsPayDTO, PaymentOrder.class);
        paymentOrder.setMerchantId(merchantId);
        paymentOrder.setBdNo(bdNo);
        paymentOrder.setCreateDate(DateUtil.getCurrDate());
        //TODO 写死环迅标识
        paymentOrder.setPlatformTag("hx");
        paymentOrder.setPayUserId(jsPayVO.getUserId());

        paymentOrder = objectToPaymenOrder(paymentOrder, jsonObject);
        log.info("paymentOrder:{}", paymentOrder);
        //插入订单交易记录
        paymentOrderMapper.insert(paymentOrder);

        return JSONObject.fromObject(paymentOrder.getPayInfo()).toString();
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 实体转化
     **/
    private PaymentOrder objectToPaymenOrder(PaymentOrder paymentOrder, JSONObject jsonObject) {
        //平台商户号
        paymentOrder.setPlatformMerchantId(jsonObject.getString(ApiConstants.MERCHANT_NO_NAME));
        if (jsonObject.has(ApiConstants.TEADE_NO_NAME) && StringUtil.isNotBlank(jsonObject.getString(ApiConstants.TEADE_NO_NAME))) {
            //平台订单号
            paymentOrder.setPlatformOrderId(jsonObject.getString(ApiConstants.TEADE_NO_NAME));
        }
        //平台支付状态转换系统支付状态
        if (jsonObject.has(ApiConstants.TRADE_STATUS_NAME)) {
            paymentOrder.setPayStatus(tradeStatusToPayStatus(jsonObject.getString(ApiConstants.TRADE_STATUS_NAME)));
        }
        if (jsonObject.has(ApiConstants.TRADE_TIME_NAME)) {
            paymentOrder.setCreateTime(DateUtil.format(jsonObject.getString(ApiConstants.TRADE_TIME_NAME), DateUtil.YYYYMMDDHHMMSS));
        }
        if (jsonObject.has(ApiConstants.PAY_INFO_NAME)) {
            paymentOrder.setPayInfo(jsonObject.getString(ApiConstants.PAY_INFO_NAME));
        }
        return paymentOrder;
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 平台支付状态转换系统支付状态
     **/
    private int tradeStatusToPayStatus(String tradeStatus) {
        int payStatus = PayConstants.PAY_STATUS_TO_BE_PAY;//默认待支付
        switch (tradeStatus) {
            case ApiConstants.TRADE_STATUS_SUCCESS:
                payStatus = PayConstants.PAY_STATUS_SUCCESS;
                break;
            case ApiConstants.TRADE_STATUS_PROCESSING:
                payStatus = PayConstants.PAY_STATUS_TO_BE_PAY;
                break;
            case ApiConstants.TRADE_STATUS_FAIL:
                payStatus = PayConstants.PAY_STATUS_FAIL;
                break;
            case ApiConstants.TRADE_STATUS_CLOSE:
                payStatus = PayConstants.PAY_STATUS_CLOSE;
                break;
            case ApiConstants.TRADE_STATUS_REFUND:
                payStatus = PayConstants.PAY_STATUS_REFUND;
                break;
        }
        return payStatus;
    }

    /**
     * @author hdq
     * @date 2020/8/18
     * @desc 从商户签约的产品中根据支付类型获取产品id
     **/
    private String getProductByPayType(Integer payType, List<ProductResponse> productResponses) {
        for (ProductResponse productRespons : productResponses) {
            if (productRespons.getPayType().equals(payType)) {
                return productRespons.getProductCode();
            }
        }
        return null;
    }

}



























