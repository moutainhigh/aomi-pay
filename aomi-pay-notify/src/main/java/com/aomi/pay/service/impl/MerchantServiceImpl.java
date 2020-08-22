package com.aomi.pay.service.impl;

import com.aomi.pay.constants.SysConstants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantAudioBind;
import com.aomi.pay.mapper.user.MerchantAudioBindMapper;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.util.CommonExceptionUtils;
import com.aomi.pay.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/18
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantAudioBindMapper merchantAudioBindMapper;

    /**
     * 根据商户id获取播报设备列表
     *
     * @param merchantId 商户id
     */
    @Override
    public List<MerchantAudioBind> queryAudiosByMerchantId(Long merchantId) throws Exception {
        log.info("--------根据商户id获取播报设备列表开始--------merchantId:{}", merchantId);
        List<MerchantAudioBind> merchantAudioBinds = merchantAudioBindMapper.selectList(new LambdaQueryWrapper<MerchantAudioBind>().eq(MerchantAudioBind::getMerchantId, merchantId));
        log.info("--------根据商户id获取播报设备列表结束--------merchantAudioBinds:{}", merchantAudioBinds);
        return merchantAudioBinds;
    }
}



























