package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.util.DateUtil;
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
//分页参数
        int current =0;
        int size =6;
        //拼装参数构造器
        QueryWrapper<NewProducts> newProductsqueryWrapper = new QueryWrapper<>();
        newProductsqueryWrapper.eq("status",1);
        newProductsqueryWrapper.orderByDesc("create_time");

        Page<NewProducts> page = new Page<>(current, size);
        IPage<NewProducts> newProductsPage = this.newProductsMapper.selectPage(page, newProductsqueryWrapper);
        List<NewProducts> newProducts = newProductsPage.getRecords();
        List<RotationChart> rotationCharts = this.rotationChartMapper.selectList(new LambdaQueryWrapper<RotationChart>().le(RotationChart::getGroudingTime, DateUtil.getCurrDate()).ge(RotationChart::getValidityTime,DateUtil.getCurrDate()).eq(RotationChart::getStatus,1));
        QueryWrapper<AppAdvertise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.orderByDesc("create_time");
        List<AppAdvertise> appAdvertises = this.appAdvertiseMapper.selectList(queryWrapper);
        mapResult.put("rotationCharts",rotationCharts);
        mapResult.put("appAdvertises",appAdvertises);
        mapResult.put("newProducts",newProducts);
        return mapResult;
    }
}
