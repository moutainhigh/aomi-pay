package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 库存表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
public interface StockMapper extends BaseMapper<Stock> {
    public List<Stock> checkStore(@Param("skuId") Integer skuId, @Param("num") Integer num);
    public int lockStore(@Param("skuId") Integer skuId, @Param("num") Integer num);
    public int unlockStore(@Param("skuId") Integer skuId, @Param("num") Integer num);

}



























