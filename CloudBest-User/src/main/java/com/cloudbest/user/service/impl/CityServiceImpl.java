package com.cloudbest.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.user.entity.City;
import com.cloudbest.user.entity.CustomerAddr;
import com.cloudbest.user.mapper.CityMapper;
import com.cloudbest.user.service.CityService;
import com.cloudbest.user.vo.CityVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> listCity(City city) {
        if(city==null){
            throw new BusinessException(CommonErrorCode.E_900116.getCode(),"地址对象为空");
        }
        List<City> cities = cityMapper.listCity(city.getLevel(),city.getParentId());
        return cities;
    }

    @Override
    public City queryById(Integer id) {
        City city = cityMapper.selectById(id);
        return city;
    }

    @Override
    public List<City> queryList(CityVO info) {
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
        QueryWrapper<City> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getCode()!=null){
            queryWrapper.eq("code", info.getId());
        }
        if (info.getName()!=null){
            queryWrapper.eq("name", info.getId());
        }
        if (info.getParentId()!=null){
            queryWrapper.eq("parent_id", info.getId());
        }
        if (info.getLevel()!=null){
            queryWrapper.eq("level", info.getId());
        }
        Page<City> page = new Page<>(current, size);
        IPage<City> cityIPage = cityMapper.selectPage(page, queryWrapper);
        if (cityIPage.getTotal()>0){
            return cityIPage.getRecords();
        }
        return new ArrayList<>();
    }
}
