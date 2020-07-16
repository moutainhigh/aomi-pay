package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.RotationChart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotationChartMapper extends BaseMapper<RotationChart> {
    List<RotationChart> selectRotationCharts();
}
