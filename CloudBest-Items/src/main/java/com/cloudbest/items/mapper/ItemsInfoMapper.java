package com.cloudbest.items.mapper;

import com.cloudbest.items.entity.ItemsInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ItemsInfoMapper extends BaseMapper<ItemsInfo> {

    @Select("select * from c_items_info where is_view=1 and NOW() between grouding_time and validity_time ORDER BY RAND() LIMIT 10")
    List<ItemsInfo> selectFavorityList(@Param("date") Date date);
}
