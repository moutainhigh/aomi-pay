package com.aomi.pay.service;

import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.JsPayRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 商户Service
 *
 * @author : hdq
 * @date 2020/8/18
 */
public interface MerchantService {

    /**
     * 根据固码编号  查商户简称
     */
    String queryByQrCode(String fixedQrCode) throws Exception;

    /**
     * 根据固码编号  查商户信息
     */
    GetMerchantInfoResponse queryInfoByQrCode(String fixedQrCode) throws Exception;

}
