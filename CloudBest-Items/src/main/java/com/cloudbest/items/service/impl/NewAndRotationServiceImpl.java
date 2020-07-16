package com.cloudbest.items.service.impl;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.items.entity.AppAdvertise;
import com.cloudbest.items.entity.NewProducts;
import com.cloudbest.items.entity.RotationChart;
import com.cloudbest.items.mapper.AppAdvertiseMapper;
import com.cloudbest.items.mapper.NewProductsMapper;
import com.cloudbest.items.mapper.RotationChartMapper;
import com.cloudbest.items.service.NewAndRotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewAndRotationServiceImpl implements NewAndRotationService {
    @Autowired
    private NewProductsMapper newProductsMapper;
    @Autowired
    private RotationChartMapper rotationChartMapper;
    @Autowired
    private AppAdvertiseMapper appAdvertiseMapper;
    @Override
    public Map<String,Object> selectNewProductsById( ) {
        Map<String,Object> mapResult = new HashMap<>();
        List<NewProducts> newProducts = this.newProductsMapper.selectNewProducts();
        List<RotationChart> rotationCharts = this.rotationChartMapper.selectRotationCharts();
        List<AppAdvertise> appAdvertises = this.appAdvertiseMapper.selectAppAdvertises();
        mapResult.put("rotationCharts",rotationCharts);
        mapResult.put("appAdvertises",appAdvertises);
        mapResult.put("newProducts",newProducts);
        return mapResult;
    }

    @Override
    public void addNewProductsById(NewProducts newProducts) {
        if (newProducts==null){
            throw  new BusinessException(CommonErrorCode.FAIL.getCode(),"添加新品不存在");
        }
        this.newProductsMapper.insert(newProducts);
    }

    @Override
    public void addRotationChart(RotationChart rotationChart) {
        if (rotationChart==null){
            throw  new BusinessException(CommonErrorCode.FAIL.getCode(),"添加轮播图不存在");
        }
        this.rotationChartMapper.insert(rotationChart);
    }

    @Override
    public void addaAppAdvertise(AppAdvertise appAdvertise) {
        if (appAdvertise==null){
            throw  new BusinessException(CommonErrorCode.FAIL.getCode(),"添加广告不存在");
        }
        this.appAdvertiseMapper.insert(appAdvertise);
    }
}
