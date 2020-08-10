package com.aomi.pay.service.impl;

import com.aomi.pay.mapper.MerchantImgMapper;
import com.aomi.pay.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class ImageServiceImpl implements ImageService {
    @Autowired
    private MerchantImgMapper merchantImgMapper;
//    @Override
//    public void addImage(MerchantImg merchantImg) {
//        merchantImg.setUpdateTime(LocalDateTime.now());
//        this.merchantImgMapper.insert(merchantImg);
//    }
}
