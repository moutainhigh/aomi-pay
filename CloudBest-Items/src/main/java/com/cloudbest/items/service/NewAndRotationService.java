package com.cloudbest.items.service;

import java.util.Map;

public interface NewAndRotationService {
    Map<String,Object> selectNewProductsById();

    //RotationChart selectRotationChartById(Integer id);
}
