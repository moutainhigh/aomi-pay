package com.cloudbest.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.order.entity.ItemEntity;
import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.feign.ItemClient;
import com.cloudbest.order.mapper.ItemMapper;
import com.cloudbest.order.mapper.MainMapper;
import com.cloudbest.order.mapper.SecondarilyMapper;
import com.cloudbest.order.otherentity.CItemsImg;
import com.cloudbest.order.service.ItemService;
import com.cloudbest.order.vo.ItemVO;
import com.cloudbest.order.vo.MainEntityVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private MainMapper mainMapper;
    @Autowired
    private SecondarilyMapper secondarilyMapper;
    @Autowired
    private ItemClient itemClient;

    //查询所有订单详情
    @Override
    public MainEntityVO secltItemsById(String id,String token) {
        try {
            TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.E_110003);//暂定
        }
        if(id==null){
            throw new BusinessException(CommonErrorCode.E_300122);//暂定
        }
        MainEntity entity = mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, id));
        SecondarilyEntity secondarilyEntity = secondarilyMapper.selectOne(new LambdaQueryWrapper<SecondarilyEntity>().eq(SecondarilyEntity::getMainOrderId, id));

        LocalDateTime orderTime = entity.getOrderTime();

        MainEntityVO entityVO = new MainEntityVO();

//        Date.from(orderTime.toInstant(ZoneOffset.of("+8")));
        LocalDateTime overTime = orderTime.plusMinutes(15);
        Date date = Date.from(overTime.toInstant(ZoneOffset.of("+8")));
        Long over = DateUtil.betweenMs(com.cloudbest.common.util.DateUtil.getCurrDate(),date);
        entityVO.setOverTime(over/1000);

        entityVO.setReceiverName(secondarilyEntity.getReceiverName());
        entityVO.setReceiverPhone(secondarilyEntity.getReceiverPhone());
        entityVO.setReceiverDetailAddress(secondarilyEntity.getReceiverDetailAddress());

        BeanUtils.copyProperties(entity,entityVO);
        QueryWrapper<ItemEntity> itemEntityQueryWrapper = new QueryWrapper<>();
        itemEntityQueryWrapper.eq("order_id",entity.getMainOrderId());
        List<ItemEntity> itemEntityList = itemMapper.selectList(itemEntityQueryWrapper);
        for (ItemEntity itemEntity:itemEntityList){
            entityVO.setSkuId(itemEntity.getProductId());
            entityVO.setSpuId(itemEntity.getItemId());
            entityVO.setCount(itemEntity.getProductQuantity());
            entityVO.setPrice(itemEntity.getProductPrice());
            entityVO.setName(itemEntity.getProductName());
            //根据商品id名称查询图片信息
            Result skuImg = itemClient.selecImgBySkuId(itemEntity.getProductId());
            String toJSONStringImg = JSON.toJSONString(skuImg.getData());
            CItemsImg cItemsImg = JSON.parseObject(toJSONStringImg, CItemsImg.class);
            entityVO.setImg(cItemsImg.getImgUrl());
        }
//        List<ItemEntity> itemEntities = this.itemMapper.selectList(new LambdaQueryWrapper<ItemEntity>().eq(ItemEntity::getOrderId, id));
        return entityVO;
    }

    public static void main(String[] args) {
        test();
    }
    public static void test(){
        //DateUtil.betweenMs(com.cloudbest.common.util.DateUtil.getCurrDate());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime dateTime = now.plusMinutes(15);
        System.out.println(dateTime);
    }


    @Override
    public ItemEntity findById(Integer id)  throws BusinessException {
        if(id==null){
            throw new  BusinessException(CommonErrorCode.E_300120);
        }
        ItemEntity item = itemMapper.selectOne(new LambdaQueryWrapper<ItemEntity>().eq(ItemEntity::getId, id));
        return item;
    }

    @Override
    public ItemEntity createItemOrder(ItemEntity itemEntity) {
        if(itemEntity==null){
            throw new  BusinessException(CommonErrorCode.E_300121);
        }
        itemMapper.insert(itemEntity);
        return itemEntity;
    }

    @Override
    public ItemEntity updateItemOrder(ItemEntity itemEntity) {
        if(itemEntity==null ){
            throw new  BusinessException(CommonErrorCode.E_300122);
        }
        itemMapper.updateById(itemEntity);
        return itemEntity;
    }



    @Override
    public List<ItemEntity> queryList(ItemVO info) {
        //分页参数
        int current =0;
        int size =0;
        if (info.getCurrent()!=null&&info.getSize()!=null){
            current = info.getCurrent();
            size = info.getSize();
        }else {
            current = 0;
            size = 200;
        }
        //查询可用图片
        QueryWrapper<ItemEntity> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getOrderId()!=null){
            queryWrapper.eq("order_id", info.getOrderId());
        }
        Page<ItemEntity> page = new Page<>(current, size);
        IPage<ItemEntity> itemEntityIPage = itemMapper.selectPage(page, queryWrapper);
        if (itemEntityIPage.getTotal()>0){
            return itemEntityIPage.getRecords();
        }
        return new ArrayList<>();
    }
}
