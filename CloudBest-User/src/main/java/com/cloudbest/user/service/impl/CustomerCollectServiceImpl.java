package com.cloudbest.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.CustomerCollect;
import com.cloudbest.user.feign.ItemClient;
import com.cloudbest.user.mapper.CustomerCollectMapper;
import com.cloudbest.user.service.CustomerCollectService;
import com.cloudbest.user.vo.CustomerAddrVO;
import com.cloudbest.user.vo.CustomerCollectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 会员收藏的商品 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CustomerCollectServiceImpl implements CustomerCollectService {
    @Autowired
    private CustomerCollectMapper customerCollectMapper;
    @Autowired
    private ItemClient itemClient ;
    @Override
    public List<Result> queryCollect(String token) throws Exception{
        if(token==null){
            throw new BusinessException(CommonErrorCode.E_900121.getCode(),"token信息为空");
        }
        List<CustomerCollect> collects = customerCollectMapper.selectList(new LambdaQueryWrapper<CustomerCollect>().eq(CustomerCollect::getCustomerId, TokenUtil.getUserId(token)));
        List<Result> itemsInfoVOS = new ArrayList<>();
        for (CustomerCollect c : collects) {
            Integer id = c.getGoodsId();
            Result itemsInfoVO = itemClient.getItemInfoById(id);
            itemsInfoVOS.add(itemsInfoVO);
        }
        return itemsInfoVOS;
    }

    @Override
    public void addCollect(CustomerCollect customerCollect) {
        if (customerCollect==null){
            throw new BusinessException(CommonErrorCode.E_900120.getCode(),"用户收藏为空");
        }
        customerCollectMapper.insert(customerCollect);
    }

    @Override
    public void delateCollect(Integer id) {
        if (id==null){
            throw new BusinessException(CommonErrorCode.E_900120.getCode(),"用户收藏为空");
        }
        customerCollectMapper.delete(new LambdaQueryWrapper<CustomerCollect>().eq(CustomerCollect::getId,id));
    }

    @Override
    public List<CustomerCollect> queryList(CustomerCollectVO info) {
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
        QueryWrapper<CustomerCollect> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getCustomerId()!=null){
            queryWrapper.eq("customer_id", info.getCustomerId());
        }
        if (info.getGoodsId()!=null){
            queryWrapper.eq("goods_id", info.getGoodsId());
        }
        Page<CustomerCollect> page = new Page<>(current, size);
        IPage<CustomerCollect> customerCollectIPage = customerCollectMapper.selectPage(page, queryWrapper);
        if (customerCollectIPage.getTotal()>0){
            return customerCollectIPage.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public CustomerCollect queryById(Integer id) {
        CustomerCollect customerCollect = customerCollectMapper.selectById(id);
        return customerCollect;
    }

}
