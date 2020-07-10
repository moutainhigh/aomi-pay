package com.cloudbest.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.enums.StatusEnum;
import com.cloudbest.order.mapper.SecondarilyMapper;
import com.cloudbest.order.service.SecondarilyService;
import com.cloudbest.order.vo.SecondarilyVO;
import com.cloudbest.order.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class SecondarilyServiceImpl implements SecondarilyService {

    @Autowired
    private SecondarilyMapper secondarilyMapper;

    @Override
    public int createSecondarilyOrder(SecondarilyEntity secondarilyEntity) {
        if(secondarilyEntity ==null ){
            throw new BusinessException(CommonErrorCode.E_300121);
        }
        int insert = secondarilyMapper.insert(secondarilyEntity);
        return insert;//枚举类，创建了几条信息
    }


    @Override
    public List<String> selectDeliveryStatus(String id) {
        if(id==null ){
            throw new BusinessException(CommonErrorCode.FAIL);
        }
        List<SecondarilyEntity> secondarilyEntitys = this.secondarilyMapper.selectList(new LambdaQueryWrapper<SecondarilyEntity>().eq(SecondarilyEntity::getMainOrderId, id));

        List<String> collect = secondarilyEntitys.stream().map(secondarilyEntity -> {
            String ancillaryOrderId = secondarilyEntity.getAncillaryOrderId();
            if (StringUtil.isEmpty(ancillaryOrderId)) {
                return null;
            }
            String deliveryStatus = secondarilyEntity.getDeliveryStatus();
            return deliveryStatus;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public SecondarilyEntity selectByid(Integer id) {
        if(id==null){
            throw new BusinessException(CommonErrorCode.E_300110);
        }
        SecondarilyEntity secondarilyEntity = secondarilyMapper.selectById(id);
        return secondarilyEntity;
    }

    @Override
    public SecondarilyEntity updateSecondarilyOrder(SecondarilyEntity secondarilyEntity) {
        if(secondarilyEntity ==null|| secondarilyEntity.getId()==null){

            throw new BusinessException(CommonErrorCode.E_300122);
        }
        secondarilyMapper.updateById(secondarilyEntity);
        return secondarilyEntity;
    }

    @Override
    public void selectUserInfoById(Long id) {
        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setUsernam("");
        userInfoVO.setPhone(123123);
        userInfoVO.setAddress("");


    }

    @Override
    public SecondarilyEntity offSecondarily(Integer id) {
        SecondarilyEntity secondarilyEntity = secondarilyMapper.selectById(id);
        if(secondarilyEntity==null||secondarilyEntity.getId()==null){
            throw new BusinessException(CommonErrorCode.E_300122);
        }
        secondarilyEntity.setDeleteStatus(0);
        secondarilyMapper.updateById(secondarilyEntity);
        return secondarilyEntity;
    }

    @Override
    public List<SecondarilyEntity> queryList(SecondarilyVO info) {
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
        QueryWrapper<SecondarilyEntity> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getMainOrderId()!=null){
            queryWrapper.eq("main_order_id", info.getMainOrderId());
        }
        if (info.getAncillaryOrderId()!=null){
            queryWrapper.eq("ancillary_order_id", info.getAncillaryOrderId());
        }
        if (info.getReceiverName()!=null){
            queryWrapper.eq("receiver_name", info.getReceiverName());
        }
        if (info.getDeliveryId()!=null){
            queryWrapper.eq("delivery_id", info.getDeliveryId());
        }
        if (info.getDeleteStatus()!=null){
            queryWrapper.eq("delete_status", info.getDeleteStatus());
        }else {
            queryWrapper.eq("delete_status", StatusEnum.SHELVES.getValue());
        }
        Page<SecondarilyEntity> page = new Page<>(current, size);
        IPage<SecondarilyEntity> secondarilyEntityIPage = secondarilyMapper.selectPage(page, queryWrapper);
        if (secondarilyEntityIPage.getTotal()>0){
            return secondarilyEntityIPage.getRecords();
        }
        return new ArrayList<>();
    }



    //根据主订单号查询子订单
    @Override
    public SecondarilyEntity selectSecondarilyOrderById(String id) {

        if(id ==null ){
            throw new BusinessException(CommonErrorCode.FAIL);//暂定
        }
        SecondarilyEntity secondarilyEntity = this.secondarilyMapper.selectOne(new LambdaQueryWrapper<SecondarilyEntity>().eq(SecondarilyEntity::getMainOrderId, id));
        return secondarilyEntity;
    }


}
