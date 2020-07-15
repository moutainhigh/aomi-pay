package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.NewProducts;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewProductsMapper extends BaseMapper<NewProducts> {
    List<NewProducts> selectNewProducts();
}
