package com.cloudbest.items.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Promotion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品详情表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Repository
public interface PromotionMapper extends BaseMapper<Promotion> {

}
