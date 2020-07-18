package com.cloudbest.items.service.impl;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.common.util.GeneralConvertorUtil;
import com.cloudbest.items.entity.PurchaseLimit;
import com.cloudbest.items.mapper.PurchaseLimitMapper;
import com.cloudbest.items.service.PurchaseLimitService;
import com.cloudbest.items.vo.PurchaseLimitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 限购规则表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020/7/18 11:46
 */
@Slf4j
@Service
@Transactional
public class PurchaseLimitServiceImpl implements PurchaseLimitService {

    @Resource
    private PurchaseLimitMapper purchaseLimitMapper;

    String errorMessage = "网络小憩~请稍后再试！";

    @Override
    public void add(PurchaseLimitVO purchaseLimitVO) throws Exception{
        //查询该商品sku是否存在规则
        PurchaseLimit param = GeneralConvertorUtil.convertor(purchaseLimitVO, PurchaseLimit.class);

        PurchaseLimit purchaseLimit = purchaseLimitMapper.selectByItemIdSkuId(param);

        if(!StringUtils.isEmpty(purchaseLimit)){
            throw new BusinessException(CommonErrorCode.E_901015.getCode(),CommonErrorCode.E_901015.getDesc());
        }
        //TODO 待合并二期代码整改constants  频率暂默认写定为1
        param.setPurchaseLimitFrequency(param.getPurchaseLimitFrequency() == null ? 1 : param.getPurchaseLimitFrequency());
        param.setUpdateTime(param.getUpdateTime() == null ? DateUtil.getCurrDate() : param.getUpdateTime());

        int result = purchaseLimitMapper.insert(param);

        if (result==0){
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),"商品限购规则添加失败");
        }
    }

    @Override
    public void update(PurchaseLimitVO purchaseLimitVO) {
        //查询该商品sku是否存在规则
        PurchaseLimit param = GeneralConvertorUtil.convertor(purchaseLimitVO, PurchaseLimit.class);

        PurchaseLimit purchaseLimit = purchaseLimitMapper.selectById(param.getId());

        if(StringUtils.isEmpty(purchaseLimit)){
            throw new BusinessException(CommonErrorCode.E_901016.getCode(),CommonErrorCode.E_901016.getDesc());
        }

        param.setPurchaseLimitFrequency(param.getPurchaseLimitFrequency() == null ? 1 : param.getPurchaseLimitFrequency());
        param.setUpdateTime(DateUtil.getCurrDate());
        param.setId(purchaseLimit.getId());

        int result = purchaseLimitMapper.updateById(param);

        if (result==0){
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),"商品限购规则修改失败");
        }
    }

    @Override
    public PurchaseLimitVO getByItemIdSkuId(PurchaseLimitVO purchaseLimitVO) {

        PurchaseLimit param = GeneralConvertorUtil.convertor(purchaseLimitVO, PurchaseLimit.class);

        PurchaseLimit purchaseLimit = purchaseLimitMapper.selectByItemIdSkuId(param);

        if(StringUtils.isEmpty(purchaseLimit)){
            return null;
        }else{
            return GeneralConvertorUtil.convertor(purchaseLimit, PurchaseLimitVO.class);
        }
    }

    @Override
    public void delete(PurchaseLimitVO purchaseLimitVO) {
        PurchaseLimit purchaseLimit = purchaseLimitMapper.selectById(purchaseLimitVO.getId());

        if(StringUtils.isEmpty(purchaseLimit)){
            throw new BusinessException(CommonErrorCode.E_901016.getCode(),CommonErrorCode.E_901016.getDesc());
        }

        int result = purchaseLimitMapper.deleteById(purchaseLimitVO.getId());

        if (result==0){
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),"商品限购规则删除失败");
        }

    }

}
