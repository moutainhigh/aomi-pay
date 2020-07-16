package com.cloudbest.items.service;

import com.cloudbest.items.entity.AppAdvertise;
import com.cloudbest.items.entity.NewProducts;
import com.cloudbest.items.entity.RotationChart;

import java.util.Map;

public interface NewAndRotationService {
    Map<String,Object> selectNewProductsById();

    void addNewProductsById(NewProducts newProducts);

    void addRotationChart(RotationChart rotationChart);

    void addaAppAdvertise(AppAdvertise appAdvertise);

    //RotationChart selectRotationChartById(Integer id);
}
