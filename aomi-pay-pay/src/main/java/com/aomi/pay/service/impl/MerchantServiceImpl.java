package com.aomi.pay.service.impl;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantInfo;
import com.aomi.pay.entity.MerchantProduct;
import com.aomi.pay.entity.MerchantProductBind;
import com.aomi.pay.entity.MerchantQrBind;
import com.aomi.pay.mapper.user.MerchantInfoMapper;
import com.aomi.pay.mapper.user.MerchantProductBindMapper;
import com.aomi.pay.mapper.user.MerchantProductMapper;
import com.aomi.pay.mapper.user.MerchantQrBindMapper;
import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.ProductResponse;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.util.CommonExceptionUtils;
import com.aomi.pay.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantQrBindMapper merchantQrBindMapper;

    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Resource
    private MerchantProductBindMapper merchantProductBindMapper;

    @Resource
    private MerchantProductMapper merchantProductMapper;

    /**
     * 根据固码编号  查商户简称
     */
    @Override
    @Cacheable(value = "merchantSimpleCache", key = "#fixedQrCode")
    public String queryByQrCode(String fixedQrCode) {
        MerchantQrBind merchantQrBind = merchantQrBindMapper.selectOne(new LambdaQueryWrapper<MerchantQrBind>().eq(MerchantQrBind::getFixedQrCode, fixedQrCode));
        if (StringUtil.isEmpty(fixedQrCode)) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301012);
        }
        MerchantInfo merchantInfo = this.merchantInfoMapper.selectById(merchantQrBind.getMerchantId());
        if (StringUtils.isEmpty(merchantInfo)) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301013);
        }
        return merchantInfo.getSimpleName();
    }

    /**
     * 根据固码编号  查商户信息
     */
    @Override
    @Cacheable(value = "merchantInfoCache", key = "#fixedQrCode")
    public GetMerchantInfoResponse queryInfoByQrCode(String fixedQrCode) {
        log.info("--------根据固码编号查商户信息开始--------");
        MerchantQrBind merchantQrBind = merchantQrBindMapper.selectOne(new LambdaQueryWrapper<MerchantQrBind>().eq(MerchantQrBind::getFixedQrCode, fixedQrCode));

        if (StringUtil.isEmpty(fixedQrCode)) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301012);
        }
        Long merchantId = merchantQrBind.getMerchantId();
        MerchantInfo merchantInfo = this.merchantInfoMapper.selectById(merchantId);

        if (StringUtils.isEmpty(merchantInfo)) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301013);
        }

        GetMerchantInfoResponse res = new GetMerchantInfoResponse();
        res.setBdNo(merchantInfo.getBdNo());
        res.setId(merchantInfo.getId());
        res.setPlatformId(merchantInfo.getPlatformId());
        res.setSimpleName(merchantInfo.getSimpleName());
        //根据商户号查询状态为1的产品列表
        List<MerchantProductBind> merchantProductBinds = this.merchantProductBindMapper.selectList(new LambdaQueryWrapper<MerchantProductBind>().eq(MerchantProductBind::getMerchantId, merchantId).eq(MerchantProductBind::getState, 1));

        if (merchantProductBinds.isEmpty()) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301014);
        }

        List<Integer> productIds = merchantProductBinds.stream().map(merchantProductBind -> {
            return merchantProductBind.getProductBindId();
        }).collect(Collectors.toList());

        List<MerchantProduct> merchantProductList = merchantProductMapper.selectBatchIds(productIds);

        if (merchantProductList.isEmpty()) {
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.E_301014);
        }

        //封装产品利率信息
        List<ProductResponse> productResponses = merchantProductList.stream().map(merchantProduct -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setModelId(merchantProduct.getModelId());
            productResponse.setProductCode(merchantProduct.getProductCode());
            productResponse.setPayType(merchantProduct.getPayType());
            return productResponse;
        }).collect(Collectors.toList());

        res.setProductList(productResponses);
        log.info("--------根据固码编号查商户信息结束--------result:{}", res);
        return res;
    }

}



























