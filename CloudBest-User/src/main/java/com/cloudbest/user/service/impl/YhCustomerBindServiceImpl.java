package com.cloudbest.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.entity.YhCustomerBind;
import com.cloudbest.user.mapper.CityMapper;
import com.cloudbest.user.mapper.YhCustomerBindMapper;
import com.cloudbest.user.service.CityService;
import com.cloudbest.user.service.YhCustomerBindService;
import com.cloudbest.user.vo.CityVO;
import com.cloudbest.user.vo.YhCustomerBindVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class YhCustomerBindServiceImpl implements YhCustomerBindService {

    @Autowired
    private YhCustomerBindMapper customerBindMapper;

    @Override
    public YhCustomerBind creatNewCustomerBind(YhCustomerBind customerBind) {
        customerBind.setBindTime(LocalDateTime.now());
        customerBindMapper.insert(customerBind);
        return customerBind;
    }

    @Override
    public YhCustomerBind updateCustomerBind(YhCustomerBind customerBind) {
        customerBindMapper.updateById(customerBind);
        return customerBind;
    }

    @Override
    public List<YhCustomerBind> queryList(YhCustomerBindVO info) {
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
        QueryWrapper<YhCustomerBind> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getCloudUserId()!=null){
            queryWrapper.eq("cloud_user_id", info.getCloudUserId());
        }
        if (info.getYhUserId()!=null){
            queryWrapper.eq("yh_user_id", info.getYhUserId());
        }
        Page<YhCustomerBind> page = new Page<>(current, size);
        IPage<YhCustomerBind> customerBindIPage = customerBindMapper.selectPage(page, queryWrapper);
        if (customerBindIPage.getTotal()>0){
            return customerBindIPage.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public YhCustomerBind queryById(Integer id) {
        YhCustomerBind customerBind = customerBindMapper.selectById(id);
        return customerBind;
    }

    @Override
    public YhCustomerBind offCustomerBind(Integer id) {
        YhCustomerBind customerBind = customerBindMapper.selectById(id);
        if (customerBind!=null){
            throw new BusinessException(CommonErrorCode.E_900133.getCode(),"尚未与有惠绑定");
        }
        return customerBind;
    }
}
