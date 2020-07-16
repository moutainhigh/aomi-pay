package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.AppAdvertise;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppAdvertiseMapper extends BaseMapper<AppAdvertise> {
    List<AppAdvertise> selectAppAdvertises();
}
